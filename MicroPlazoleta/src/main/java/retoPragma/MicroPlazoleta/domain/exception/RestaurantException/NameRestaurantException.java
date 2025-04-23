package retoPragma.MicroPlazoleta.domain.exception.RestaurantException;

public class NameRestaurantException extends RuntimeException {
    public NameRestaurantException() {
        super("El nombre de el restaurante no puede ser solo numeros. Ejemplo:'Mi Restaurante 21'");
    }
}
