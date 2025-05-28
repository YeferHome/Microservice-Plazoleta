package retoPragma.MicroPlazoleta.infrastructure.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.DishEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoEntityMapper {
    DishEntity toPlatoEntity(Dish dish);

    Dish toPlato(DishEntity dishEntity);
}
