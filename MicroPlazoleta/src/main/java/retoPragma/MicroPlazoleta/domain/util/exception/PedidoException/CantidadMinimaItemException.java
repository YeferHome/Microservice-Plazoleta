package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class CantidadMinimaItemException extends RuntimeException {
    public CantidadMinimaItemException() {
        super("La cantidad del item debe ser mayor que 0");
    }
}
