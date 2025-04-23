package retoPragma.MicroPlazoleta.domain.exception.RestaurantException;

public class NoOwnerException extends RuntimeException {
    public NoOwnerException() {
        super("El usuario no tiene rol de propietario.");
    }
}
