package retoPragma.MicroPlazoleta.domain.exception.PedidoException;

public class PlatoNoPerteneceARestauranteException extends RuntimeException {
    public PlatoNoPerteneceARestauranteException() {
        super("El plato no pertenece al restaurante." );
    }
}
