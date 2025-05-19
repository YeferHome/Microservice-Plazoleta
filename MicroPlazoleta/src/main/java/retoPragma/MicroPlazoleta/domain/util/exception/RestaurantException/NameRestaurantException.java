package retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException;

public class NameRestaurantException extends RuntimeException {
    public NameRestaurantException() {
        super("El nombre de el restaurante no puede ser solo numeros. Ejemplo:'Mi Restaurante 21'");
    }
}
