package retoPragma.MicroPlazoleta.infrastructure.output.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoEntityMapper {

    PedidoEntity toPedidoEntity(Order order);
    Order toPedido(PedidoEntity pedidoEntity);
}