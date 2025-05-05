package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.model.PedidoItem;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoAppResponseMapper {

    PedidoResponseDto toPedidoResponseDto(Pedido pedido);


    PedidoItemResponseDto toPedidoItemResponseDto(PedidoItem pedidoItem);
}