package retoPragma.MicroPlazoleta.infrastructure.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PlatoEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoEntityMapper {
    PlatoEntity toPlatoEntity(Dish dish);

    Dish toPlato(PlatoEntity platoEntity);
}
