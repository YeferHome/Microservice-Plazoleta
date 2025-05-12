package retoPragma.MicroPlazoleta.domain.exception.PedidoException;

public class PedidoEnProcesoException extends RuntimeException {
    public PedidoEnProcesoException() {
        super("Este Cliente ya tiene un pedido en proceso");
    }
}
