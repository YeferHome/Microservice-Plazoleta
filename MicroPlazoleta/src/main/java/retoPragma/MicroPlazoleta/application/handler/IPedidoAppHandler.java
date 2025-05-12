package retoPragma.MicroPlazoleta.application.handler;

import org.springframework.data.domain.Page;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

public interface IPedidoAppHandler {

    PedidoResponseDto savePedido(PedidoRequestDto requestDto);
    Page<Pedido> getPedidosPorEstado(long restauranteId, EstadoPedido estado, int page, int size);

}
