package retoPragma.MicroPlazoleta.infrastructure.output.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoEntityMapper {

    OrderEntity toPedidoEntity(Order order);
    Order toPedido(OrderEntity orderEntity);
}