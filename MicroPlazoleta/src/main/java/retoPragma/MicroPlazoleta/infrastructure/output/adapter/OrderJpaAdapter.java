package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderItemEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IOrderRepository;

import java.util.List;

@AllArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IPedidoEntityMapper orderEntityMapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toPedidoEntity(order);

        if (orderEntity.getItems() != null) {
            for (OrderItemEntity itemEntity : orderEntity.getItems()) {
                itemEntity.setOrder(orderEntity);
            }
        }

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return orderEntityMapper.toPedido(savedOrderEntity);
    }

    @Override
    public boolean userHaveOrderActive(Long idUser) {
        return orderRepository.existsByIdClientAndEstateIn(
                idUser,
                List.of(EstateOrder.PENDIENTE, EstateOrder.EN_PREPARACION, EstateOrder.LISTO));
    }

    @Override
    public PageModel<Order> findOrderByStateRestaurant(EstateOrder estate, Long restaurantId, PageRequestModel pageRequestModel) {
        Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());

        Page<OrderEntity> pageResult = orderRepository.findByEstateAndIdRestaurant(
                estate,
                restaurantId,
                pageable
        );

        List<Order> content = pageResult.getContent().stream()
                .map(orderEntityMapper::toPedido)
                .toList();

        return new PageModel<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements()
        );
    }
}
