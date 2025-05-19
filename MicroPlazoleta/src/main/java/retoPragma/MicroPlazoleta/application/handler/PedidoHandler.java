package retoPragma.MicroPlazoleta.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IPedidoAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPedidoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoHandler implements IPedidoAppHandler{

    private final IOrderServicePort pedidoServicePort;
    private final IPedidoAppRequestMapper pedidoRequestMapper;
    private final IPedidoAppResponseMapper pedidoResponseMapper;

    @Override
    public PedidoResponseDto savePedido(PedidoRequestDto requestDto) {
        Order order = pedidoRequestMapper.toPedido(requestDto);

        Order orderCreado = pedidoServicePort.saveOrder(order);

        return pedidoResponseMapper.toPedidoResponseDto(orderCreado);
    }

    @Override
    public Page<Order> getPedidosPorEstado(Long restauranteId, EstateOrder estado, int page, int size) {

        return pedidoServicePort.getOrderByStates(restauranteId, estado, page , size);
    }

}