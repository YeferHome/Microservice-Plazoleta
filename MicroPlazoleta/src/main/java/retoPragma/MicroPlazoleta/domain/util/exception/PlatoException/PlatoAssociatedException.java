package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class PlatoAssociatedException extends RuntimeException {
    public PlatoAssociatedException() {
        super("El plato debe estar asociado a un restaurante válido.");
    }
}
