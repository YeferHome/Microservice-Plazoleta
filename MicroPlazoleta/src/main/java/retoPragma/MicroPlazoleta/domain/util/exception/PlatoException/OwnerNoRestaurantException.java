package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class OwnerNoRestaurantException extends RuntimeException {
    public OwnerNoRestaurantException() {
        super("No eres propietario de este restaurante, no puedes modificar este plato.");
    }
}
