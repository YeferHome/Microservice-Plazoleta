package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class PedidoProcesoException extends RuntimeException {
    public PedidoProcesoException() {
        super("El usuario ya tiene un pedido en proceso.");
    }
}
