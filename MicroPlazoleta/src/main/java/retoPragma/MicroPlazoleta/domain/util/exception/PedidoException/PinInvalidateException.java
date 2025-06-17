package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class PinInvalidateException extends RuntimeException {
    public PinInvalidateException() {
        super("El PIN ingresado no es válido.");
    }
}
