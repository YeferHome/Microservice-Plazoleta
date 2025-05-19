package retoPragma.MicroPlazoleta.domain.spi;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IOrderPersistencePort {

    Order saveOrder(Order order);
    boolean userHaveOrderActive(Long idUser);
    Page<Order> findOrderByStateRestaurant(EstateOrder estate, Long restaurantId, int page, int size);
}