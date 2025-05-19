package retoPragma.MicroPlazoleta.application.mapper;

import retoPragma.MicroPlazoleta.application.dto.DishAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.DishUpdateEstateRequestDto;
import retoPragma.MicroPlazoleta.application.dto.DishUpdateRequestDto;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishAppRequestMapper {

    Dish toDish(DishAppRequestDto dishAppRequestDto);
    Dish toDishUpdate(DishUpdateRequestDto dishUpdateRequestDto);
    String toEstateString(DishUpdateEstateRequestDto dishUpdateEstateRequestDto);
}
