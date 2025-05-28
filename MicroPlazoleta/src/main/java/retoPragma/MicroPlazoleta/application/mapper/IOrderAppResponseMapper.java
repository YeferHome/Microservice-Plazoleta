package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;
import retoPragma.MicroPlazoleta.application.dto.OrderItemResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderAppResponseMapper {

    OrderResponseDto toOrderResponseDto(Order order);


    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);
}