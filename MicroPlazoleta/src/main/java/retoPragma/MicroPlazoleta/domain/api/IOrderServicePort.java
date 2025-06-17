package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IOrderServicePort {

    Order saveOrder(Order order);
    PageModel<Order> getOrderByStates(long restaurantId, EstateOrder estate, PageRequestModel pageRequestModel);
    Order assignEmployeeAndSetInPreparation(Long orderId, Long employeeId);
    Order markOrderAsDone(Long orderId, String token);
    Order markOrderAsDelivered(Long orderId, String pin);
    Order cancelOrder(Long orderId);
}
