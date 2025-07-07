package retoPragma.MicroPlazoleta.domain.util.exception;

public class ClienteNoAutorizadoException extends RuntimeException {
    public ClienteNoAutorizadoException() {
        super("El cliente no está autorizado para realizar esta acción.");
    }
}
