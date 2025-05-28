package retoPragma.MicroPlazoleta.application.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.application.dto.OrderItemResponseDto;
import retoPragma.MicroPlazoleta.application.dto.OrderRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IOrderAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IOrderAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderHandlerTest {

    private IOrderServicePort orderServicePort;
    private IOrderAppRequestMapper orderRequestMapper;
    private IOrderAppResponseMapper orderResponseMapper;
    private OrderHandler orderHandler;

    @BeforeEach
    void setUp() {
        orderServicePort = mock(IOrderServicePort.class);
        orderRequestMapper = mock(IOrderAppRequestMapper.class);
        orderResponseMapper = mock(IOrderAppResponseMapper.class);
        orderHandler = new OrderHandler(orderServicePort, orderRequestMapper, orderResponseMapper);
    }

    @Test
    void saveOrder() {
        OrderRequestDto requestDto = new OrderRequestDto();
        Order domainOrder = new Order();
        Order createdOrder = new Order();

        List<OrderItemResponseDto> itemsResponse = List.of();

        OrderResponseDto expectedResponse = new OrderResponseDto(
                1L,
                EstateOrder.PENDIENTE,
                10L,
                20L,
                itemsResponse
        );

        when(orderRequestMapper.toOrder(requestDto)).thenReturn(domainOrder);
        when(orderServicePort.saveOrder(domainOrder)).thenReturn(createdOrder);
        when(orderResponseMapper.toOrderResponseDto(createdOrder)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.saveOrder(requestDto);

        assertNotNull(result);
        assertEquals(expectedResponse.getIdPedido(), result.getIdPedido());
        assertEquals(expectedResponse.getEstado(), result.getEstado());
        assertEquals(expectedResponse.getIdCliente(), result.getIdCliente());
        assertEquals(expectedResponse.getIdRestaurante(), result.getIdRestaurante());
        assertEquals(expectedResponse.getItems(), result.getItems());

        verify(orderRequestMapper).toOrder(requestDto);
        verify(orderServicePort).saveOrder(domainOrder);
        verify(orderResponseMapper).toOrderResponseDto(createdOrder);
    }

    @Test
    void getOrderByEstate() {
        Long restaurantId = 1L;
        EstateOrder state = EstateOrder.PENDIENTE;
        int page = 0;
        int size = 10;

        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> domainOrders = List.of(order1, order2);
        PageModel<Order> pageModel = new PageModel<>(domainOrders, page, size, domainOrders.size());

        OrderResponseDto responseDto1 = new OrderResponseDto(1L, state, 10L, 100L, List.of());
        OrderResponseDto responseDto2 = new OrderResponseDto(2L, state, 11L, 101L, List.of());

        when(orderServicePort.getOrderByStates(eq(restaurantId), eq(state), any(PageRequestModel.class)))
                .thenReturn(pageModel);
        when(orderResponseMapper.toOrderResponseDto(order1)).thenReturn(responseDto1);
        when(orderResponseMapper.toOrderResponseDto(order2)).thenReturn(responseDto2);

        PageResponseDto<OrderResponseDto> result = orderHandler.getOrderByEstate(restaurantId, state, page, size);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(responseDto1.getIdPedido(), result.getContent().get(0).getIdPedido());
        assertEquals(responseDto2.getIdPedido(), result.getContent().get(1).getIdPedido());

        verify(orderServicePort).getOrderByStates(eq(restaurantId), eq(state), any(PageRequestModel.class));
        verify(orderResponseMapper).toOrderResponseDto(order1);
        verify(orderResponseMapper).toOrderResponseDto(order2);
    }
}
