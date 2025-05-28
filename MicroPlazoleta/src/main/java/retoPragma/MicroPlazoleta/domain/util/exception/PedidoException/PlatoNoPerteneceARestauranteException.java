package retoPragma.MicroPlazoleta.domain.util.exception.PedidoException;

public class PlatoNoPerteneceARestauranteException extends RuntimeException {
    public PlatoNoPerteneceARestauranteException() {
        super("El plato no pertenece al restaurante." );
    }
}
