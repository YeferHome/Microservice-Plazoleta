package retoPragma.MicroPlazoleta.domain.exception.PedidoException;

public class PlatoErrorException extends RuntimeException {
    public PlatoErrorException() {
        super("Todos los platos deben ser del mismo restaurante.");
    }
}
