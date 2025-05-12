package retoPragma.MicroPlazoleta.domain.spi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

public interface IPedidoPersistencePort {

    Pedido savePedido(Pedido pedido);
    boolean usuarioTienePedidoActivo(Long idUsuario);
    Page<Pedido> findPedidosPorEstadoYRestaurante(EstadoPedido estado, Long restauranteId, PageRequest pageRequest);
}