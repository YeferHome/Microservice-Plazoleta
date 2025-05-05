package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Pedido;

public interface IPedidoServicePort {

    Pedido savePedido(Pedido pedido);
}
