package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class EmpleadoPerteneceRestauranteException extends RuntimeException {
    public EmpleadoPerteneceRestauranteException() {
        super("El empleado no pertenece a este restaurante.");
    }
}
