package retoPragma.MicroPlazoleta.application.handler;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.application.dto.OrderRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;

import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IOrderAppHandler {

    OrderResponseDto saveOrder(OrderRequestDto requestDto);
    PageResponseDto<OrderResponseDto> getOrderByEstate(Long restaurantId, EstateOrder estate, int page, int size);


}
