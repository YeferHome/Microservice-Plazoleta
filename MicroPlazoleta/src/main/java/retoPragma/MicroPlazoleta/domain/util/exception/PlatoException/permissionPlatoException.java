package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class permissionPlatoException extends RuntimeException {
    public permissionPlatoException() {
        super("El usuario no tiene permisos para modificar platos.");
    }
}
