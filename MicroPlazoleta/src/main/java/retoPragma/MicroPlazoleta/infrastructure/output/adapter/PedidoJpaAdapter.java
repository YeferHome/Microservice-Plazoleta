package retoPragma.MicroPlazoleta.infrastructure.output.adapter;


import lombok.AllArgsConstructor;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.spi.IPedidoPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoItemEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPedidoRepository;

import java.util.List;

@AllArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;


    @Override
    public Pedido savePedido(Pedido pedido) {
        PedidoEntity pedidoEntity = pedidoEntityMapper.toPedidoEntity(pedido);

        if (pedidoEntity.getItems() != null) {
            for (PedidoItemEntity itemEntity : pedidoEntity.getItems()) {
                itemEntity.setPedido(pedidoEntity);
            }
        }

        PedidoEntity savedPedidoEntity = pedidoRepository.save(pedidoEntity);
        return pedidoEntityMapper.toPedido(savedPedidoEntity);
    }

    @Override
    public boolean usuarioTienePedidoActivo(Long idUsuario) {

        return pedidoRepository.existsByIdClienteAndEstadoIn(
                idUsuario,
                List.of(EstadoPedido.PENDIENTE, EstadoPedido.EN_PREPARACION, EstadoPedido.LISTO));
    }
   // ------------------------------------------------------------
}
