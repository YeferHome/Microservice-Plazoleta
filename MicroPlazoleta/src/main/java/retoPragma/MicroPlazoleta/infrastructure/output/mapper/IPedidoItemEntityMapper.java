package retoPragma.MicroPlazoleta.infrastructure.output.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.OrderItem;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderItemEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoItemEntityMapper {

    OrderItemEntity toPedidoItemEntity(OrderItem orderItem);
    OrderItem toPedidoItem(OrderItemEntity orderItemEntity);
}