package retoPragma.MicroPlazoleta.domain.exception.PedidoException;

public class NoPedidoExcepcion extends RuntimeException
{
    public NoPedidoExcepcion() {
        super("El pedido no tiene platos.");
    }
}
