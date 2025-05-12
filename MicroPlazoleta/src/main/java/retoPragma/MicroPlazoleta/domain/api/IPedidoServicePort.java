package retoPragma.MicroPlazoleta.domain.api;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

public interface IPedidoServicePort {

    Pedido savePedido(Pedido pedido);
    Page<Pedido> getPedidosPorEstados(long restauranteId, EstadoPedido estado, int page, int size);
}
