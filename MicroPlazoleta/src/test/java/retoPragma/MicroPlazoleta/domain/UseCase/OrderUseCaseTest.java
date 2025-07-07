package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retoPragma.MicroPlazoleta.domain.api.IMessagingServicePort;
import retoPragma.MicroPlazoleta.domain.api.ITraceabilityServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.*;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.ClienteNoAutorizadoException;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.*;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock private IOrderPersistencePort orderPersistencePort;
    @Mock private IUserServicePort userServicePort;
    @Mock private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock private IMessagingServicePort messagingServicePort;
    @Mock private ITraceabilityServicePort traceabilityServicePort;

    @InjectMocks private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(
                orderPersistencePort,
                userServicePort,
                restaurantPersistencePort,
                messagingServicePort,
                traceabilityServicePort
        );
    }

    private Order buildValidOrder(EstateOrder estate, String pin) {
        OrderItem item = new OrderItem(1L, 1L, 1L, 1);
        return new Order(1L, 1L, 1L, estate, List.of(item), 2L, pin);
    }

    private void mockPlatoBelongsToRestaurant() {
        when(restaurantPersistencePort.platoBelongsRestaurant(anyLong(), anyLong())).thenReturn(true);
    }

    @Test
    void saveOrder_success() {
        Order order = buildValidOrder(null, null);
        when(orderPersistencePort.userHaveOrderActive(1L)).thenReturn(false);
        mockPlatoBelongsToRestaurant();
        when(orderPersistencePort.saveOrder(any())).thenReturn(order);

        Order saved = orderUseCase.saveOrder(order);

        assertNotNull(saved);
        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void saveOrder_shouldThrowIfClientHasActiveOrder() {
        Order order = buildValidOrder(null, null);
        when(orderPersistencePort.userHaveOrderActive(1L)).thenReturn(true);
        assertThrows(PedidoEnProcesoException.class, () -> orderUseCase.saveOrder(order));
    }

    @Test
    void saveOrder_shouldThrowIfDishNotBelongsToRestaurant() {
        Order order = buildValidOrder(null, null);
        when(orderPersistencePort.userHaveOrderActive(1L)).thenReturn(false);
        when(restaurantPersistencePort.platoBelongsRestaurant(anyLong(), anyLong())).thenReturn(false);
        assertThrows(PlatoNoPerteneceARestauranteException.class, () -> orderUseCase.saveOrder(order));
    }

    @Test
    void getOrderByStates_success() {
        PageRequestModel pageRequestModel = new PageRequestModel(0, 10);
        PageModel<Order> expected = new PageModel<>(List.of(), 0, 10, 0);
        when(userServicePort.obtainRolUser(1L)).thenReturn("EMPLEADO");
        when(orderPersistencePort.findOrderByStateRestaurant(any(), anyLong(), any())).thenReturn(expected);

        PageModel<Order> result = orderUseCase.getOrderByStates(1L, EstateOrder.PENDIENTE, pageRequestModel);
        assertEquals(expected, result);
    }

    @Test
    void assignEmployeeAndSetInPreparation_success() {
        Order order = buildValidOrder(EstateOrder.PENDIENTE, null);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        when(userServicePort.obtainRolUser(2L)).thenReturn("EMPLEADO");
        mockPlatoBelongsToRestaurant();
        when(orderPersistencePort.saveOrder(any())).thenReturn(order);

        Order updated = orderUseCase.assignEmployeeAndSetInPreparation(1L, 2L);

        assertEquals(EstateOrder.EN_PREPARACION, updated.getEstate());
        verify(traceabilityServicePort).sendTraceability(any(), eq(null));
    }

    @Test
    void markOrderAsDone_success() {
        Order order = buildValidOrder(EstateOrder.EN_PREPARACION, null);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        when(userServicePort.obtainNumberPhoneClient(1L)).thenReturn("321");
        mockPlatoBelongsToRestaurant();
        when(orderPersistencePort.saveOrder(any())).thenReturn(order);

        Order result = orderUseCase.markOrderAsDone(1L, "token");

        assertEquals(EstateOrder.LISTO, result.getEstate());
        verify(messagingServicePort).sendNotification(eq("321"), anyString(), eq("token"));
        verify(traceabilityServicePort).sendTraceability(any(), eq("token"));
    }

    @Test
    void markOrderAsDone_shouldThrowIfNotInPreparation() {
        Order order = buildValidOrder(EstateOrder.PENDIENTE, null);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        mockPlatoBelongsToRestaurant();
        assertThrows(OrderProcessException.class, () -> orderUseCase.markOrderAsDone(1L, "token"));
    }

    @Test
    void markOrderAsDelivered_success() {
        Order order = buildValidOrder(EstateOrder.LISTO, "123");
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        mockPlatoBelongsToRestaurant();
        when(orderPersistencePort.saveOrder(any())).thenReturn(order);

        Order updated = orderUseCase.markOrderAsDelivered(1L, "123");

        assertEquals(EstateOrder.ENTREGADO, updated.getEstate());
        verify(traceabilityServicePort).sendTraceability(any(), eq(null));
    }

    @Test
    void markOrderAsDelivered_shouldThrowIfInvalidPin() {
        Order order = buildValidOrder(EstateOrder.LISTO, "abc");
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        mockPlatoBelongsToRestaurant();
        assertThrows(PinInvalidateException.class, () -> orderUseCase.markOrderAsDelivered(1L, "wrong"));
    }

    @Test
    void markOrderAsDelivered_shouldThrowIfNotReady() {
        Order order = buildValidOrder(EstateOrder.EN_PREPARACION, "123");
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        mockPlatoBelongsToRestaurant();
        assertThrows(OrderProcessException.class, () -> orderUseCase.markOrderAsDelivered(1L, "123"));
    }

    @Test
    void cancelOrder_success() {
        Order order = buildValidOrder(EstateOrder.PENDIENTE, null);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        when(orderPersistencePort.saveOrder(any())).thenReturn(order);

        Order updated = orderUseCase.cancelOrder(1L, 1L);
        assertEquals(EstateOrder.CANCELADO, updated.getEstate());
        verify(traceabilityServicePort).sendTraceability(any(), eq(null));
    }

    @Test
    void cancelOrder_shouldThrowIfNotPending() {
        Order order = buildValidOrder(EstateOrder.EN_PREPARACION, null);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        assertThrows(OrderCanceledException.class, () -> orderUseCase.cancelOrder(1L, 1L));
    }

    @Test
    void cancelOrder_shouldThrowIfUnauthorized() {
        Order order = buildValidOrder(EstateOrder.PENDIENTE, null);
        when(orderPersistencePort.findById(1L)).thenReturn(order);
        assertThrows(ClienteNoAutorizadoException.class, () -> orderUseCase.cancelOrder(1L, 2L));
    }

    @Test
    void existsById_shouldReturnTrue() {
        when(orderPersistencePort.findById(1L)).thenReturn(new Order());
        assertTrue(orderUseCase.existsById(1L));
    }

    @Test
    void existsById_shouldReturnFalse() {
        when(orderPersistencePort.findById(1L)).thenReturn(null);
        assertFalse(orderUseCase.existsById(1L));
    }
}
