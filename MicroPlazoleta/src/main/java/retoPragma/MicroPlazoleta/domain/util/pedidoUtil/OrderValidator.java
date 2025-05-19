package retoPragma.MicroPlazoleta.domain.util.pedidoUtil;

import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.*;

import static retoPragma.MicroPlazoleta.domain.util.pedidoUtil.PedidoConstants.CANTIDAD_MINIMA;

public class OrderValidator {

    private final IRestaurantPersistencePort restaurantePersistencePort;

    public OrderValidator(IUserServicePort usuarioServicePort, IOrderPersistencePort pedidoPersistencePort, IRestaurantPersistencePort restaurantePersistencePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    public void validarPedido(Order order) {
        if (order == null) {
            throw new NoNullExcepcion();
        }

        if (order.getIdClient() == null) {
            throw new ClienteNoNullExcepcion();
        }

        if (order.getIdRestaurant() == null) {
            throw new IdRestaurantNoNullExcepcion();
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new ItemException();
        }

        for (OrderItem item : order.getItems()) {
            if (item.getIdDish() == null) {
                throw new IdPlatoException();
            }

            if (item.getAmount() <= CANTIDAD_MINIMA) {
                throw new CantidadMinimaItemException();
            }

            boolean pertenece = restaurantePersistencePort
                    .platoBelongsRestaurant(item.getIdDish(), order.getIdRestaurant());

            if (!pertenece) {
                throw new PlatoNoPerteneceARestauranteException();
            }
        }
    }
}

