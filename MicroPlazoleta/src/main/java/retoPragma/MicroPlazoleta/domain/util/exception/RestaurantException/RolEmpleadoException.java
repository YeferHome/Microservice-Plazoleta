package retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException;

public class RolEmpleadoException extends RuntimeException {
    public RolEmpleadoException() {
        super("Rol de empleado no válido");
    }
}
