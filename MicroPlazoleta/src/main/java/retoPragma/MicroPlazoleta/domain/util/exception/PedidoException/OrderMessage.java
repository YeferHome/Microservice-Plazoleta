package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class OrderMessage extends RuntimeException {
    public OrderMessage() {
        super("Tu pedido está listo. Tu pin de recogida es: ");
    }
}
