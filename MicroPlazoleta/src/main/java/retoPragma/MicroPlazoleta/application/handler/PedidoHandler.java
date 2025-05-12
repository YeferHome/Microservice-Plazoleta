package retoPragma.MicroPlazoleta.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IPedidoAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPedidoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IPedidoServicePort;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoHandler implements IPedidoAppHandler{

    private final IPedidoServicePort pedidoServicePort;
    private final IPedidoAppRequestMapper pedidoRequestMapper;
    private final IPedidoAppResponseMapper pedidoResponseMapper;

    @Override
    public PedidoResponseDto savePedido(PedidoRequestDto requestDto) {
        Pedido pedido = pedidoRequestMapper.toPedido(requestDto);

        Pedido pedidoCreado = pedidoServicePort.savePedido(pedido);

        return pedidoResponseMapper.toPedidoResponseDto(pedidoCreado);
    }

    @Override
    public Page<Pedido> getPedidosPorEstado(long restauranteId, EstadoPedido estado, int page, int size) {
        return pedidoServicePort.getPedidosPorEstados(restauranteId, estado, page, size);
    }

}