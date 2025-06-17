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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void saveOrder_shouldReturnMappedResponse() {
        OrderRequestDto requestDto = new OrderRequestDto();
        Order order = new Order();
        Order savedOrder = new Order();

        OrderItemResponseDto itemDto = new OrderItemResponseDto(10L, 2);
        OrderResponseDto expectedResponse = new OrderResponseDto(
                1L, EstateOrder.PENDIENTE, 1L, 100L, List.of(itemDto), null
        );

        when(orderRequestMapper.toOrder(requestDto)).thenReturn(order);
        when(orderServicePort.saveOrder(order)).thenReturn(savedOrder);
        when(orderResponseMapper.toOrderResponseDto(savedOrder)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.saveOrder(requestDto);

        assertEquals(expectedResponse, result);
    }

    @Test
    void getOrderByEstate_shouldReturnPageResponseDto() {
        Long restaurantId = 1L;
        EstateOrder estate = EstateOrder.PENDIENTE;
        int page = 0, size = 5;

        Order order = new Order();
        PageModel<Order> pageModel = new PageModel<>(List.of(order), page, size, 1);

        OrderItemResponseDto itemDto = new OrderItemResponseDto(10L, 2);
        OrderResponseDto responseDto = new OrderResponseDto(
                1L, estate, 1L, restaurantId, List.of(itemDto), null
        );

        when(orderServicePort.getOrderByStates(eq(restaurantId), eq(estate), any(PageRequestModel.class)))
                .thenReturn(pageModel);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(responseDto);

        PageResponseDto<OrderResponseDto> result = orderHandler.getOrderByEstate(restaurantId, estate, page, size);

        assertEquals(1, result.getContent().size());
        assertEquals(responseDto, result.getContent().get(0));
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void assignEmployeeAndSetInPreparation_shouldReturnMappedResponse() {
        Long orderId = 1L;
        Long employeeId = 2L;
        Order order = new Order();

        OrderItemResponseDto itemDto = new OrderItemResponseDto(10L, 2);
        OrderResponseDto expectedResponse = new OrderResponseDto(
                1L, EstateOrder.EN_PREPARACION, 1L, 100L, List.of(itemDto), employeeId
        );

        when(orderServicePort.assignEmployeeAndSetInPreparation(orderId, employeeId)).thenReturn(order);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.assignEmployeeAndSetInPreparation(orderId, employeeId);

        assertEquals(expectedResponse, result);
        verify(orderServicePort).assignEmployeeAndSetInPreparation(orderId, employeeId);
        verify(orderResponseMapper).toOrderResponseDto(order);
    }

    @Test
    void markOrderAsDone_shouldReturnMappedResponse() {
        Long orderId = 1L;
        Order order = new Order();

        OrderItemResponseDto itemDto = new OrderItemResponseDto(10L, 2);
        OrderResponseDto expectedResponse = new OrderResponseDto(
                1L, EstateOrder.LISTO, 1L, 100L, List.of(itemDto), null
        );

        when(orderServicePort.markOrderAsDone(orderId)).thenReturn(order);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.markOrderAsDone(orderId);

        assertEquals(expectedResponse, result);
        verify(orderServicePort).markOrderAsDone(orderId);
        verify(orderResponseMapper).toOrderResponseDto(order);
    }
}
