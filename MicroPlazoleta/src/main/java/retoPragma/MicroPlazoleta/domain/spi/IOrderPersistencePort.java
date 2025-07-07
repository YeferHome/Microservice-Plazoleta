package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IOrderPersistencePort {

    Order saveOrder(Order order);
    boolean userHaveOrderActive(Long idUser);
    PageModel<Order> findOrderByStateRestaurant(EstateOrder estate, Long restaurantId, PageRequestModel pageRequestModel);
    Order findById(Long idOrder);

}
