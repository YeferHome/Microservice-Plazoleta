package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class NoPermissionEstadoExcepcion extends RuntimeException {
    public NoPermissionEstadoExcepcion() {
        super("El usuario no tiene permisos para habilitar o deshabilitar platos");
    }
}
