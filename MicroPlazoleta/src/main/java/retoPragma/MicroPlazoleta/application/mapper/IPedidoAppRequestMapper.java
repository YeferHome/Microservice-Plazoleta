package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoAppRequestMapper {

    Order toPedido(PedidoRequestDto pedidoRequestDto);

    OrderItem toPedidoItem(PedidoItemRequestDto pedidoItemRequestDto);
}