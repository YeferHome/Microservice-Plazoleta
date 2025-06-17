package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.domain.api.IMessagingServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.*;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    private IOrderPersistencePort orderPersistencePort;
    private IUserServicePort userServicePort;
    private IRestaurantPersistencePort restaurantPersistencePort;
    private IMessagingServicePort messagingServicePort;
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        orderPersistencePort = mock(IOrderPersistencePort.class);
        userServicePort = mock(IUserServicePort.class);
        restaurantPersistencePort = mock(IRestaurantPersistencePort.class);
        messagingServicePort = mock(IMessagingServicePort.class);
        orderUseCase = new OrderUseCase(orderPersistencePort, userServicePort, restaurantPersistencePort, messagingServicePort);
    }

    @Test
    void saveOrder_shouldSaveOrder_whenOrderIsValid() {
        OrderItem item1 = new OrderItem(1L, 10L, 100L, 2);
        OrderItem item2 = new OrderItem(2L, 20L, 100L, 3);
        Order order = new Order();
        order.setIdClient(1L);
        order.setIdRestaurant(100L);
        order.setItems(Arrays.asList(item1, item2));

        when(orderPersistencePort.userHaveOrderActive(1L)).thenReturn(false);
        when(restaurantPersistencePort.platoBelongsRestaurant(10L, 100L)).thenReturn(true);
        when(restaurantPersistencePort.platoBelongsRestaurant(20L, 100L)).thenReturn(true);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderUseCase.saveOrder(order);

        assertNotNull(savedOrder);
        assertEquals(EstateOrder.PENDIENTE, savedOrder.getEstate());
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void saveOrder_shouldThrowException_whenUserHasActiveOrder() {
        Order order = new Order();
        order.setIdClient(1L);
        order.setIdRestaurant(100L);
        order.setItems(List.of(new OrderItem(1L, 10L, 100L, 1)));

        when(orderPersistencePort.userHaveOrderActive(1L)).thenReturn(true);

        assertThrows(PedidoEnProcesoException.class, () -> orderUseCase.saveOrder(order));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void getOrderByStates_shouldReturnPage_whenEmployeeValid() {
        long restaurantId = 100L;
        PageRequestModel request = new PageRequestModel(0, 10);
        EstateOrder estate = EstateOrder.PENDIENTE;

        PageModel<Order> expected = new PageModel<>(List.of(new Order()), 0, 10, 1);
        when(userServicePort.obtainRolUser(anyLong())).thenReturn("EMPLEADO");
        when(orderPersistencePort.findOrderByStateRestaurant(estate, restaurantId, request)).thenReturn(expected);

        PageModel<Order> result = orderUseCase.getOrderByStates(restaurantId, estate, request);

        assertEquals(expected.getContent(), result.getContent());
    }

    @Test
    void getOrderByStates_shouldThrow_whenInvalidRole() {
        when(userServicePort.obtainRolUser(anyLong())).thenReturn("ADMIN");

        assertThrows(EmpleadoPerteneceRestauranteException.class, () ->
                orderUseCase.getOrderByStates(1L, EstateOrder.PENDIENTE, new PageRequestModel(0, 10)));
    }

    @Test
    void assignEmployeeAndSetInPreparation_shouldWork_whenValid() {
        Order order = new Order();
        order.setIdClient(1L);
        order.setIdRestaurant(100L);
        order.setItems(List.of(new OrderItem(1L, 10L, 100L, 2)));

        when(orderPersistencePort.findById(1L)).thenReturn(order);
        when(userServicePort.obtainRolUser(5L)).thenReturn("EMPLEADO");
        when(restaurantPersistencePort.platoBelongsRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderUseCase.assignEmployeeAndSetInPreparation(1L, 5L);

        assertEquals(5L, result.getEmployeeAssigned());
        assertEquals(EstateOrder.EN_PREPARACION, result.getEstate());
    }

    @Test
    void markOrderAsDone_shouldSetReadyAndNotify_whenInPreparation() {
        Order order = new Order();
        order.setEstate(EstateOrder.EN_PREPARACION);
        order.setIdClient(1L);
        order.setPin("1234");
        order.setIdRestaurant(100L);
        order.setItems(List.of(new OrderItem(1L, 10L, 100L, 2)));

        when(orderPersistencePort.findById(1L)).thenReturn(order);
        when(userServicePort.obtainRolUser(anyLong())).thenReturn("EMPLEADO");
        when(restaurantPersistencePort.platoBelongsRestaurant(anyLong(), anyLong())).thenReturn(true);
        when(userServicePort.obtainNumberPhoneClient(1L)).thenReturn("+573001234567");

        Order result = orderUseCase.markOrderAsDone(1L);

        assertEquals(EstateOrder.LISTO, result.getEstate());
        verify(messagingServicePort).sendNotification(eq("+573001234567"), anyString());
    }

    @Test
    void markOrderAsDone_shouldThrow_whenNotInPreparation() {
        Order order = new Order();
        order.setEstate(EstateOrder.PENDIENTE);

        when(orderPersistencePort.findById(1L)).thenReturn(order);

        assertThrows(OrderProcessException.class, () -> orderUseCase.markOrderAsDone(1L));
    }
}
