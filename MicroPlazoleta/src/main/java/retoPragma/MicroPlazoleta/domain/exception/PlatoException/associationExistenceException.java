package retoPragma.MicroPlazoleta.domain.exception.PlatoException;

public class associationExistenceException extends RuntimeException {
    public associationExistenceException() {
        super("El restaurante asociado al plato no existe.");
    }
}
