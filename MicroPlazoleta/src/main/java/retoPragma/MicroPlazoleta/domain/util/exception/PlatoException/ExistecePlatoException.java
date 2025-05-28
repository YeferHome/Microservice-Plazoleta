package retoPragma.MicroPlazoleta.domain.util.exception.PlatoException;

public class ExistecePlatoException extends RuntimeException {
    public ExistecePlatoException() {
        super("El plato que intentas actualizar no existe.");
    }
}
