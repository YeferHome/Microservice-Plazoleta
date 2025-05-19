package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class PricePlatoException extends RuntimeException {
    public PricePlatoException() {
        super("El Plato debe tener un Precio positivo y mayor que 0");
    }
}
