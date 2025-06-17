package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class OrderProcessException extends RuntimeException {
    public OrderProcessException() {
        super("Solo los pedidos en preparación pueden ser marcados como listos.");
    }
}
