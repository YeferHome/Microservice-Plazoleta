package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.model.PedidoItem;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoAppRequestMapper {

    Pedido toPedido(PedidoRequestDto pedidoRequestDto);

    PedidoItem toPedidoItem(PedidoItemRequestDto pedidoItemRequestDto);
}