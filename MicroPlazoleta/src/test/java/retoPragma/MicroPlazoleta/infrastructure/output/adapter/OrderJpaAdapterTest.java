package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderItemEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IOrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderJpaAdapterTest {

    private IOrderRepository pedidoRepository;
    private IPedidoEntityMapper pedidoEntityMapper;
    private OrderJpaAdapter pedidoJpaAdapter;

    @BeforeEach
    void setUp() {
        pedidoRepository = mock(IOrderRepository.class);
        pedidoEntityMapper = mock(IPedidoEntityMapper.class);
        pedidoJpaAdapter = new OrderJpaAdapter(pedidoRepository, pedidoEntityMapper);
    }

    @Test
    void savePedido() {
        Order order = mock(Order.class);
        OrderEntity orderEntityWithItems = new OrderEntity();
        OrderItemEntity itemEntity = new OrderItemEntity();
        orderEntityWithItems.setItems(List.of(itemEntity));

        OrderEntity savedOrderEntity = new OrderEntity();
        Order savedOrder = mock(Order.class);

        when(pedidoEntityMapper.toPedidoEntity(order)).thenReturn(orderEntityWithItems);
        when(pedidoRepository.save(orderEntityWithItems)).thenReturn(savedOrderEntity);
        when(pedidoEntityMapper.toPedido(savedOrderEntity)).thenReturn(savedOrder);

        Order result = pedidoJpaAdapter.saveOrder(order);

        assertEquals(orderEntityWithItems, itemEntity.getOrder());

        verify(pedidoEntityMapper).toPedidoEntity(order);
        verify(pedidoRepository).save(orderEntityWithItems);
        verify(pedidoEntityMapper).toPedido(savedOrderEntity);

        assertEquals(savedOrder, result);
    }

    @Test
    void usuarioTienePedidoActivo() {
        Long idUsuario = 1L;
        when(pedidoRepository.existsByIdClientAndEstateIn(
                eq(idUsuario), ArgumentMatchers.anyList()))
                .thenReturn(true);

        boolean tienePedidoActivo = pedidoJpaAdapter.userHaveOrderActive(idUsuario);

        verify(pedidoRepository).existsByIdClientAndEstateIn(
                eq(idUsuario), ArgumentMatchers.anyList());

        assertTrue(tienePedidoActivo);
    }

    @Test
    void findPedidosPorEstadoYRestaurante() {
        EstateOrder estado = EstateOrder.PENDIENTE;
        Long restauranteId = 1L;
        PageRequestModel pageRequestModel = new PageRequestModel(0, 5);
        Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());

        OrderEntity orderEntity = new OrderEntity();
        List<OrderEntity> orderEntityList = List.of(orderEntity);
        Page<OrderEntity> pedidoEntityPage = new PageImpl<>(orderEntityList);

        Order order = mock(Order.class);

        when(pedidoRepository.findByEstateAndIdRestaurant(estado, restauranteId, pageable))
                .thenReturn(pedidoEntityPage);
        when(pedidoEntityMapper.toPedido(orderEntity)).thenReturn(order);

        PageModel<Order> resultPage = pedidoJpaAdapter.findOrderByStateRestaurant(estado, restauranteId, pageRequestModel);

        verify(pedidoRepository).findByEstateAndIdRestaurant(estado, restauranteId, pageable);
        verify(pedidoEntityMapper).toPedido(orderEntity);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(order, resultPage.getContent().get(0));
    }
}
