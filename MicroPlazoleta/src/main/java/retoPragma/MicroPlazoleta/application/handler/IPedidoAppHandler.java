package retoPragma.MicroPlazoleta.application.handler;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;

import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

public interface IPedidoAppHandler {

    PedidoResponseDto savePedido(PedidoRequestDto requestDto);
    Page<Order> getPedidosPorEstado(Long restauranteId, EstateOrder estado, int page, int size);


}
