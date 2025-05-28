package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class NoPlatoException extends RuntimeException {
    public NoPlatoException() {
        super("No existe plato");
    }
}
