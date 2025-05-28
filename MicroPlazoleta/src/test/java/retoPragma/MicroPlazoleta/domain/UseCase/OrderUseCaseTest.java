package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.*;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort pedidoPersistencePort;
    @Mock
    private IUserServicePort usuarioServicePort;
    @Mock
    private IRestaurantPersistencePort restaurantePersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        OrderItem item = new OrderItem(1L, 100L, 10L, 2);
        order = new Order(1L, 200L, 10L, EstateOrder.PENDIENTE, List.of(item));
    }

    @Test
    void savePedido_Exitoso() {
        when(pedidoPersistencePort.userHaveOrderActive(200L)).thenReturn(false);
        when(restaurantePersistencePort.platoBelongsRestaurant(100L, 10L)).thenReturn(true);
        when(pedidoPersistencePort.saveOrder(order)).thenReturn(order);

        Order resultado = orderUseCase.saveOrder(order);

        assertNotNull(resultado);
        verify(pedidoPersistencePort).saveOrder(order);
    }

    @Test
    void savePedido_ConPedidoActivo_LanzaExcepcion() {
        when(pedidoPersistencePort.userHaveOrderActive(200L)).thenReturn(true);

        assertThrows(PedidoEnProcesoException.class, () -> orderUseCase.saveOrder(order));
    }

    @Test
    void savePedido_PlatoNoPerteneceARestaurante_LanzaExcepcion() {
        when(pedidoPersistencePort.userHaveOrderActive(200L)).thenReturn(false);
        when(restaurantePersistencePort.platoBelongsRestaurant(100L, 10L)).thenReturn(false);

        assertThrows(PlatoNoPerteneceARestauranteException.class, () -> orderUseCase.saveOrder(order));
    }

    @Test
    void savePedido_ConCantidadMenor_LanzaExcepcion() {
        OrderItem item = new OrderItem(1L, 100L, 10L, 0);
        Order orderInvalido = new Order(1L, 200L, 10L, EstateOrder.PENDIENTE, List.of(item));

        when(pedidoPersistencePort.userHaveOrderActive(200L)).thenReturn(false);
        when(restaurantePersistencePort.platoBelongsRestaurant(100L, 10L)).thenReturn(true);

        assertThrows(CantidadMinimaItemException.class, () -> orderUseCase.saveOrder(orderInvalido));
    }

    @Test
    void getPedidosPorEstados_Exitoso() {
        long restauranteId = 10L;
        EstateOrder estado = EstateOrder.PENDIENTE;
        int page = 0;
        int size = 5;

        List<Order> orders = List.of(order);
        PageModel<Order> pageResultado = new PageModel<>(orders, page, size, orders.size());

        when(restaurantePersistencePort.employeeBelongsRestaurant(restauranteId)).thenReturn(true);
        when(pedidoPersistencePort.findOrderByStateRestaurant(estado, restauranteId, new PageRequestModel(page, size)))
                .thenReturn(pageResultado);

        PageModel<Order> resultado = orderUseCase.getOrderByStates(restauranteId, estado, new PageRequestModel(page, size));

        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    void getPedidosPorEstados_EmpleadoNoPertenece_LanzaExcepcion() {
        when(restaurantePersistencePort.employeeBelongsRestaurant(10L)).thenReturn(false);

        assertThrows(EmpleadoPerteneceRestauranteException.class, () ->
                orderUseCase.getOrderByStates(10L, EstateOrder.PENDIENTE, new PageRequestModel(0, 5)));
    }
}
