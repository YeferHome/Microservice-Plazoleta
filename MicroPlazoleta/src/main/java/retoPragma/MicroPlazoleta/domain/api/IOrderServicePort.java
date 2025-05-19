package retoPragma.MicroPlazoleta.domain.api;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IOrderServicePort {

    Order saveOrder(Order order);
    Page<Order> getOrderByStates(long restauranteId, EstateOrder estado, int page, int size);
}
