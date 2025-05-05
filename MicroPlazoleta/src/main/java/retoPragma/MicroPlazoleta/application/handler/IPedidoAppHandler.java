package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;

public interface IPedidoAppHandler {

    PedidoResponseDto savePedido(PedidoRequestDto requestDto);

}
