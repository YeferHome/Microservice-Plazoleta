package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoItemEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPedidoRepository;

import java.util.List;

@AllArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;


    @Override
    public Order saveOrder(Order order) {
        PedidoEntity pedidoEntity = pedidoEntityMapper.toPedidoEntity(order);

        if (pedidoEntity.getItems() != null) {
            for (PedidoItemEntity itemEntity : pedidoEntity.getItems()) {
                itemEntity.setPedido(pedidoEntity);
            }
        }

        PedidoEntity savedPedidoEntity = pedidoRepository.save(pedidoEntity);
        return pedidoEntityMapper.toPedido(savedPedidoEntity);
    }

    @Override
    public boolean userHaveOrderActive(Long idUser) {

        return pedidoRepository.existsByIdClienteAndEstadoIn(
                idUser,
                List.of(EstateOrder.PENDIENTE, EstateOrder.EN_PREPARACION, EstateOrder.LISTO));
    }

    @Override
    public Page<Order> findOrderByStateRestaurant(EstateOrder estate, Long restaurantId, int page, int size) {

        Page<PedidoEntity> pedidoEntities = pedidoRepository.findByEstadoAndIdRestaurante(estate, restaurantId, page, size);

        return pedidoEntities.map(pedidoEntityMapper::toPedido);
    }
}




