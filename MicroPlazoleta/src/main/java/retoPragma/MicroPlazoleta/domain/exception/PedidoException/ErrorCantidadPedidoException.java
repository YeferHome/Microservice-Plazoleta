package retoPragma.MicroPlazoleta.domain.exception.PedidoException;

public class ErrorCantidadPedidoException extends RuntimeException {
    public ErrorCantidadPedidoException() {
        super("La cantidad de cada plato debe ser mayor a cero.");
    }
}
