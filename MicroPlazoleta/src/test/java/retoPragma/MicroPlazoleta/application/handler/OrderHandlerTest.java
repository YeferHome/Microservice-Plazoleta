package retoPragma.MicroPlazoleta.application.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IPedidoAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPedidoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderHandlerTest {

    private IOrderServicePort pedidoServicePort;
    private IPedidoAppRequestMapper pedidoRequestMapper;
    private IPedidoAppResponseMapper pedidoResponseMapper;
    private PedidoHandler pedidoHandler;

    @BeforeEach
    void setUp() {
        pedidoServicePort = mock(IOrderServicePort.class);
        pedidoRequestMapper = mock(IPedidoAppRequestMapper.class);
        pedidoResponseMapper = mock(IPedidoAppResponseMapper.class);

        pedidoHandler = new PedidoHandler(pedidoServicePort, pedidoRequestMapper, pedidoResponseMapper);
    }

    @Test
    void savePedido() {
        PedidoRequestDto requestDto = new PedidoRequestDto();
        Order order = new Order();
        Order orderCreado = new Order();

        List<PedidoItemResponseDto> itemsResponse = List.of();

        PedidoResponseDto responseDto = new PedidoResponseDto(
                1L,
                EstateOrder.PENDIENTE,
                10L,
                20L,
                itemsResponse
        );

        when(pedidoRequestMapper.toPedido(requestDto)).thenReturn(order);
        when(pedidoServicePort.saveOrder(order)).thenReturn(orderCreado);
        when(pedidoResponseMapper.toPedidoResponseDto(orderCreado)).thenReturn(responseDto);

        PedidoResponseDto resultado = pedidoHandler.savePedido(requestDto);

        assertNotNull(resultado);
        assertEquals(responseDto, resultado);

        verify(pedidoRequestMapper).toPedido(requestDto);
        verify(pedidoServicePort).saveOrder(order);
        verify(pedidoResponseMapper).toPedidoResponseDto(orderCreado);
    }

    @Test
    void getPedidosPorEstado() {
        Long restauranteId = 1L;
        EstateOrder estado = EstateOrder.PENDIENTE;
        int page = 0;
        int size = 10;

        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> pedidosList = List.of(order1, order2);
        Page<Order> pedidosPage = new PageImpl<>(pedidosList);

        when(pedidoServicePort.getOrderByStates(restauranteId, estado, page, size)).thenReturn(pedidosPage);

        Page<Order> resultado = pedidoHandler.getPedidosPorEstado(restauranteId, estado, page, size);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(pedidosList, resultado.getContent());

        verify(pedidoServicePort).getOrderByStates(restauranteId, estado, page, size);
    }
}
