package retoPragma.MicroPlazoleta.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.OrderRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IOrderAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IOrderAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderAppHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderAppRequestMapper orderRequestMapper;
    private final IOrderAppResponseMapper orderResponseMapper;

    @Override
    public OrderResponseDto saveOrder(OrderRequestDto requestDto) {
        Order order = orderRequestMapper.toOrder(requestDto);
        Order orderCreated = orderServicePort.saveOrder(order);
        return orderResponseMapper.toOrderResponseDto(orderCreated);
    }

    @Override
    public PageResponseDto<OrderResponseDto> getOrderByEstate(Long restaurantId, EstateOrder estate, int page, int size) {
        PageRequestModel pageRequest = new PageRequestModel(page, size);
        PageModel<Order> pageModel = orderServicePort.getOrderByStates(restaurantId, estate, pageRequest);

        List<OrderResponseDto> content = pageModel.getContent().stream()
                .map(orderResponseMapper::toOrderResponseDto)
                .collect(Collectors.toList());

        return new PageResponseDto<>(
                content,
                pageModel.getPageNumber(),
                pageModel.getPageSize(),
                pageModel.getTotalElements()
        );
    }

    @Override
    public OrderResponseDto assignEmployeeAndSetInPreparation(Long orderId, Long employeeId) {
        Order order = orderServicePort.assignEmployeeAndSetInPreparation(orderId, employeeId);
        return orderResponseMapper.toOrderResponseDto(order);
    }

    @Override
    public OrderResponseDto markOrderAsDone(Long orderId, String token) {
        Order order = orderServicePort.markOrderAsDone(orderId, token);
        return orderResponseMapper.toOrderResponseDto(order);
    }
    @Override
    public OrderResponseDto markOrderAsDelivered(Long orderId, String pin) {
        Order order = orderServicePort.markOrderAsDelivered(orderId, pin);
        return orderResponseMapper.toOrderResponseDto(order);
    }
    @Override
    public OrderResponseDto cancelOrder(Long orderId, Long clientId) {
        Order order = orderServicePort.cancelOrder(orderId, clientId);
        return orderResponseMapper.toOrderResponseDto(order);
    }
    @Override
    public boolean existsById(Long orderId) {
        return orderServicePort.existsById(orderId);
    }
}
