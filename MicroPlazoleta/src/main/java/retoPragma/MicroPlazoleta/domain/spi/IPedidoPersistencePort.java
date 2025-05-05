package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Pedido;

public interface IPedidoPersistencePort {

    Pedido savePedido(Pedido pedido);
    boolean usuarioTienePedidoActivo(Long idUsuario);

}