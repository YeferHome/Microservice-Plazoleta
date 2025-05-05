package retoPragma.MicroPlazoleta.domain.exception.PlatoException;

public class PlatoOwnerException extends RuntimeException {
    public PlatoOwnerException() {
        super("El Propietario no es dueño del restaurante la cual se va a crear el plato");
    }
}
