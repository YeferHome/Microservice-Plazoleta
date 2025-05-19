package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class IdPlatoException extends RuntimeException {
    public IdPlatoException() {
        super("El id del plato no puede ser null.");
    }
}
