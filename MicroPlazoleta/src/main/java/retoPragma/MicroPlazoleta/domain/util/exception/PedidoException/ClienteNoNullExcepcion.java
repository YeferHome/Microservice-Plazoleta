package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class ClienteNoNullExcepcion extends RuntimeException {
    public ClienteNoNullExcepcion() {
        super("El Cliente no Puede ser un valor null");
    }
}
