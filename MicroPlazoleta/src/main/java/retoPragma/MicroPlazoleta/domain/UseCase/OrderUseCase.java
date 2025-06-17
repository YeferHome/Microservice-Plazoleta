package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IMessagingServicePort;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PedidoException.*;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.OrderValidator;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.OrderMessage;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUserServicePort userServicePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IMessagingServicePort messagingServicePort;
    private final OrderValidator orderValidator;

    public OrderUseCase(
            IOrderPersistencePort orderPersistencePort,
            IUserServicePort userServicePort,
            IRestaurantPersistencePort restaurantPersistencePort,
            IMessagingServicePort messagingServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userServicePort = userServicePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.messagingServicePort = messagingServicePort;
        this.orderValidator = new OrderValidator(userServicePort, orderPersistencePort, restaurantPersistencePort);
    }

    @Override
    public Order saveOrder(Order order) {
        if (orderPersistencePort.userHaveOrderActive(order.getIdClient())) {
            throw new PedidoEnProcesoException();
        }

        for (OrderItem item : order.getItems()) {
            if (!restaurantPersistencePort.platoBelongsRestaurant(item.getIdDish(), order.getIdRestaurant())) {
                throw new PlatoNoPerteneceARestauranteException();
            }
        }

        orderValidator.validateOrder(order);
        order.setEstate(EstateOrder.PENDIENTE);

        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public PageModel<Order> getOrderByStates(long restaurantId, EstateOrder estate, PageRequestModel pageRequestModel) {
        orderValidator.validateRolEmployee(restaurantId);
        return orderPersistencePort.findOrderByStateRestaurant(estate, restaurantId, pageRequestModel);
    }

    @Override
    public Order assignEmployeeAndSetInPreparation(Long orderId, Long employeeId) {
        Order order = orderPersistencePort.findById(orderId);

        orderValidator.validateOrder(order);
        orderValidator.validateRolEmployee(employeeId);

        order.setEmployeeAssigned(employeeId);
        order.setEstate(EstateOrder.EN_PREPARACION);

        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Order markOrderAsDone(Long orderId, String token) {
        Order order = orderPersistencePort.findById(orderId);
        orderValidator.validateOrder(order);

        if (!EstateOrder.EN_PREPARACION.equals(order.getEstate())) {
            throw new OrderProcessException();
        }

        String pin = orderValidator.generarPinSeguridad();
        order.setPin(pin);
        order.setEstate(EstateOrder.LISTO);
        orderPersistencePort.saveOrder(order);

        String phone = userServicePort.obtainNumberPhoneClient(order.getIdClient());
        String message = OrderMessage.createReadyMessage(pin);

        messagingServicePort.sendNotification(phone, message,token);

        return order;
    }
    @Override
    public Order markOrderAsDelivered(Long orderId, String pin) {
        Order order = orderPersistencePort.findById(orderId);
        orderValidator.validateOrder(order);

        if (!EstateOrder.LISTO.equals(order.getEstate())) {
            throw new OrderProcessException();
        }

        if (!order.getPin().equals(pin)) {
            throw new PinInvalidateException();
        }

        order.setEstate(EstateOrder.ENTREGADO);
        return orderPersistencePort.saveOrder(order);
    }
    @Override
    public Order cancelOrder(Long orderId) {
        Order order = orderPersistencePort.findById(orderId);

        if (!EstateOrder.PENDIENTE.equals(order.getEstate())) {
            throw new OrderCanceledException();
        }

        order.setEstate(EstateOrder.CANCELADO);
        return orderPersistencePort.saveOrder(order);
    }
}
