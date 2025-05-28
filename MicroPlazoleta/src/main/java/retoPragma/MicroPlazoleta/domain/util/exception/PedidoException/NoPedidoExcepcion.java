package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class NoPedidoExcepcion extends RuntimeException
{
    public NoPedidoExcepcion() {
        super("El pedido no tiene platos.");
    }
}
