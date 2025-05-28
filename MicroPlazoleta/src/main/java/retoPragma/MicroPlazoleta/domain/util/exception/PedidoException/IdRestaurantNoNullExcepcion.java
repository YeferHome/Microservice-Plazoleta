package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class IdRestaurantNoNullExcepcion extends RuntimeException {
    public IdRestaurantNoNullExcepcion() {
        super("El id del restaurante no puede ser null.");
    }
}
