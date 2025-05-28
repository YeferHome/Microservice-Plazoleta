package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class NoPermissionCreateException extends RuntimeException {
    public NoPermissionCreateException() {
        super("El usuario no tiene permisos para crear platos.");
    }
}
