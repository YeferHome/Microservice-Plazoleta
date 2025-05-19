package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class NoNullExcepcion extends RuntimeException {
    public NoNullExcepcion() {
        super("El valor no puede ser nulo");
    }
}
