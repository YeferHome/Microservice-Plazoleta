

package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IPedidoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PedidoException.PlatoNoPerteneceARestauranteException;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.model.PedidoItem;
import retoPragma.MicroPlazoleta.domain.spi.IPedidoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;

public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IUsuarioServicePort usuarioServicePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                         IUsuarioServicePort usuarioServicePort,
                         IRestaurantePersistencePort restaurantePersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.usuarioServicePort = usuarioServicePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public Pedido savePedido(Pedido pedido) {

        for (PedidoItem item : pedido.getItems()) {
            boolean pertenece = restaurantePersistencePort
                    .platoPerteneceARestaurante(item.getIdPlato(), pedido.getIdRestaurante());

            if (!pertenece) {
                throw new PlatoNoPerteneceARestauranteException();
            }
        }
        return pedidoPersistencePort.savePedido(pedido);
    }
}
