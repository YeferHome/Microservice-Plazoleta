package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.OrderRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IOrderAppHandler {

    OrderResponseDto saveOrder(OrderRequestDto requestDto);
    PageResponseDto<OrderResponseDto> getOrderByEstate(Long restaurantId, EstateOrder estate, int page, int size);
    OrderResponseDto assignEmployeeAndSetInPreparation(Long orderId, Long employeeId);
    OrderResponseDto markOrderAsDone(Long orderId, String token);
    OrderResponseDto markOrderAsDelivered(Long orderId, String pin);
}
