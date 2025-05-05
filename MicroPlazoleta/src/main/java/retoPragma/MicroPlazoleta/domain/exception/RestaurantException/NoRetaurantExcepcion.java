package retoPragma.MicroPlazoleta.domain.exception.RestaurantException;

public class NoRetaurantExcepcion extends RuntimeException {
    public NoRetaurantExcepcion() {
        super("No existe el restaurante");
    }
}
