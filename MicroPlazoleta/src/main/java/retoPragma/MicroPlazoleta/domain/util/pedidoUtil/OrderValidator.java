package retoPragma.MicroPlazoleta.domain.util.pedidoUtil;

import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.*;

import java.util.Random;

import static retoPragma.MicroPlazoleta.domain.util.pedidoUtil.PedidoConstants.CANTIDAD_MINIMA;

public class OrderValidator {

    private final IRestaurantPersistencePort restaurantePersistencePort;
    private final IUserServicePort userServicePort;

    public OrderValidator(IUserServicePort userServicePort, IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantePersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    public String generarPinSeguridad() {
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000);
        return String.valueOf(pin);
    }

    public void validateOrder(Order order) {
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

            boolean belongs = restaurantePersistencePort
                    .platoBelongsRestaurant(item.getIdDish(), order.getIdRestaurant());

            if (!belongs) {
                throw new PlatoNoPerteneceARestauranteException();
            }
        }
    }

    public void validateRolEmployee(Long idEmployee) {
        String rol = userServicePort.obtainRolUser(idEmployee);
        if (rol == null || !rol.equals("EMPLEADO")) {
            throw new EmpleadoPerteneceRestauranteException();
        }

    }
}
