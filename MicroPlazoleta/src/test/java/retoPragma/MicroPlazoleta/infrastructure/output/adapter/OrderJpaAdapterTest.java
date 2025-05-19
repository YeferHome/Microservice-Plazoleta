package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoItemEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPedidoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderJpaAdapterTest {

    private IPedidoRepository pedidoRepository;
    private IPedidoEntityMapper pedidoEntityMapper;
    private OrderJpaAdapter pedidoJpaAdapter;

    @BeforeEach
    void setUp() {
        pedidoRepository = mock(IPedidoRepository.class);
        pedidoEntityMapper = mock(IPedidoEntityMapper.class);
        pedidoJpaAdapter = new OrderJpaAdapter(pedidoRepository, pedidoEntityMapper);
    }

    @Test
    void savePedido() {
        Order order = mock(Order.class);
        PedidoEntity pedidoEntity = new PedidoEntity();
        PedidoEntity pedidoEntityWithItems = new PedidoEntity();
        PedidoItemEntity itemEntity = new PedidoItemEntity();
        pedidoEntityWithItems.setItems(List.of(itemEntity));

        PedidoEntity savedPedidoEntity = new PedidoEntity();
        Order savedOrder = mock(Order.class);

        // Setup mocks
        when(pedidoEntityMapper.toPedidoEntity(order)).thenReturn(pedidoEntityWithItems);
        when(pedidoRepository.save(pedidoEntityWithItems)).thenReturn(savedPedidoEntity);
        when(pedidoEntityMapper.toPedido(savedPedidoEntity)).thenReturn(savedOrder);

        // Execute
        Order result = pedidoJpaAdapter.saveOrder(order);

        // Verify que el pedido fue asignado a los items
        assertEquals(pedidoEntityWithItems, itemEntity.getPedido());

        // Verificaciones
        verify(pedidoEntityMapper).toPedidoEntity(order);
        verify(pedidoRepository).save(pedidoEntityWithItems);
        verify(pedidoEntityMapper).toPedido(savedPedidoEntity);

        assertEquals(savedOrder, result);
    }

    @Test
    void usuarioTienePedidoActivo() {
        Long idUsuario = 1L;
        when(pedidoRepository.existsByIdClienteAndEstadoIn(
                eq(idUsuario),
                ArgumentMatchers.anyList()))
                .thenReturn(true);

        boolean tienePedidoActivo = pedidoJpaAdapter.userHaveOrderActive(idUsuario);

        verify(pedidoRepository).existsByIdClienteAndEstadoIn(
                eq(idUsuario),
                ArgumentMatchers.anyList());

        assertTrue(tienePedidoActivo);
    }

    @Test
    void findPedidosPorEstadoYRestaurante() {
        EstateOrder estado = EstateOrder.PENDIENTE;
        Long restauranteId = 1L;
        int page = 0;
        int size = 5;

        PedidoEntity pedidoEntity = new PedidoEntity();
        List<PedidoEntity> pedidoEntityList = List.of(pedidoEntity);
        Page<PedidoEntity> pedidoEntityPage = new PageImpl<>(pedidoEntityList);

        Order order = mock(Order.class);

        when(pedidoRepository.findByEstadoAndIdRestaurante(estado, restauranteId, page, size))
                .thenReturn(pedidoEntityPage);
        when(pedidoEntityMapper.toPedido(pedidoEntity)).thenReturn(order);

        Page<Order> resultPage = pedidoJpaAdapter.findOrderByStateRestaurant(estado, restauranteId, page, size);

        verify(pedidoRepository).findByEstadoAndIdRestaurante(estado, restauranteId, page, size);
        verify(pedidoEntityMapper).toPedido(pedidoEntity);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(order, resultPage.getContent().get(0));
    }
}
