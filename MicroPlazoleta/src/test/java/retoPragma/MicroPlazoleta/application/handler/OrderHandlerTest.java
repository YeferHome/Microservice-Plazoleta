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

    private OrderResponseDto dummyResponseDto() {
        return new OrderResponseDto(
                1L,
                EstateOrder.PENDIENTE,
                1L,
                1L,
                List.of(new OrderItemResponseDto(10L, 2)),
                2L,
                "1234"
        );
    }

    @Test
    void saveOrder_shouldReturnMappedResponse() {
        OrderRequestDto requestDto = new OrderRequestDto();
        Order order = new Order();
        Order savedOrder = new Order();
        OrderResponseDto expectedResponse = dummyResponseDto();

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
        OrderResponseDto responseDto = dummyResponseDto();

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
        OrderResponseDto expectedResponse = dummyResponseDto();

        when(orderServicePort.assignEmployeeAndSetInPreparation(orderId, employeeId)).thenReturn(order);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.assignEmployeeAndSetInPreparation(orderId, employeeId);

        assertEquals(expectedResponse, result);
    }

    @Test
    void markOrderAsDone_shouldReturnMappedResponse() {
        Long orderId = 1L;
        String token = "token";
        Order order = new Order();
        OrderResponseDto expectedResponse = dummyResponseDto();

        when(orderServicePort.markOrderAsDone(orderId, token)).thenReturn(order);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.markOrderAsDone(orderId, token);

        assertEquals(expectedResponse, result);
    }

    @Test
    void markOrderAsDelivered_shouldReturnMappedResponse() {
        Long orderId = 1L;
        String pin = "123456";
        Order order = new Order();
        OrderResponseDto expectedResponse = dummyResponseDto();

        when(orderServicePort.markOrderAsDelivered(orderId, pin)).thenReturn(order);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.markOrderAsDelivered(orderId, pin);

        assertEquals(expectedResponse, result);
    }

    @Test
    void cancelOrder_shouldReturnMappedResponse() {
        Long orderId = 1L;
        Long clientId = 2L;
        Order order = new Order();
        OrderResponseDto expectedResponse = dummyResponseDto();

        when(orderServicePort.cancelOrder(orderId, clientId)).thenReturn(order);
        when(orderResponseMapper.toOrderResponseDto(order)).thenReturn(expectedResponse);

        OrderResponseDto result = orderHandler.cancelOrder(orderId, clientId);

        assertEquals(expectedResponse, result);
    }

    @Test
    void existsById_shouldReturnTrue() {
        Long orderId = 1L;

        when(orderServicePort.existsById(orderId)).thenReturn(true);

        boolean result = orderHandler.existsById(orderId);

        assertTrue(result);
    }
}
