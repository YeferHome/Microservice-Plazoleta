package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class ItemException extends RuntimeException {
    public ItemException() {
        super("El pedido debe contener al menos un item.");
    }
}
