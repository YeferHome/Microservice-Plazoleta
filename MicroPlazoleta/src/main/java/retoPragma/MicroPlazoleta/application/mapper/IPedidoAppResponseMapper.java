package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoAppResponseMapper {

    PedidoResponseDto toPedidoResponseDto(Order order);


    PedidoItemResponseDto toPedidoItemResponseDto(OrderItem orderItem);
}