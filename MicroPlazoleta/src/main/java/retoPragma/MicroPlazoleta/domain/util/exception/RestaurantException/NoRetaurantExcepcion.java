package retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException;

public class NoRetaurantExcepcion extends RuntimeException {
    public NoRetaurantExcepcion() {
        super("No existe el restaurante");
    }
}
