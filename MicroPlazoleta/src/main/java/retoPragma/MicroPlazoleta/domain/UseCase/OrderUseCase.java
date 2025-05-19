package retoPragma.MicroPlazoleta.domain.UseCase;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.EmpleadoPerteneceRestauranteException;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.PedidoEnProcesoException;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.PlatoNoPerteneceARestauranteException;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.OrderValidator;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserServicePort userServicePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final OrderValidator orderValidator;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort,
                        IUserServicePort userServicePort,
                        IRestaurantPersistencePort restaurantPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userServicePort = userServicePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderValidator = new OrderValidator(userServicePort, orderPersistencePort, restaurantPersistencePort);
    }

    @Override
    public Order saveOrder(Order order) {

        boolean haveOrderActive = orderPersistencePort.userHaveOrderActive(order.getIdClient());
        if (haveOrderActive) {
            throw new PedidoEnProcesoException();
        }
        for (OrderItem item : order.getItems()) {
            boolean belongs = restaurantPersistencePort
                    .platoBelongsRestaurant(item.getIdDish(), order.getIdRestaurant());
            if (!belongs) {
                throw new PlatoNoPerteneceARestauranteException();
            }
        }

        orderValidator.validarPedido(order);
        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Page<Order> getOrderByStates(long restaurantId, EstateOrder estate, int page, int size) {
        if (!restaurantPersistencePort.employeeBelongsRestaurant(restaurantId)) {
            throw new EmpleadoPerteneceRestauranteException();
        }

        return orderPersistencePort.findOrderByStateRestaurant(estate, restaurantId, page, size);
    }

}
