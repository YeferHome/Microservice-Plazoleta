package retoPragma.MicroPlazoleta.domain.exception.PlatoException;

public class NoPlatoException extends RuntimeException {
    public NoPlatoException() {
        super("No existe plato");
    }
}
