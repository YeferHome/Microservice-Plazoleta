package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import retoPragma.MicroPlazoleta.application.dto.DishUpdateEstateRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoAppResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Dish;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoAppResponseMapper {

    PlatoAppResponseDto toPlatoAppResponseDto(Dish dish);
    PlatoUpdateResponseDto toPlatoUpdateResponseDto(Dish dish);
    DishUpdateEstateRequestDto toPlatoUpdateEstadoRequestDto(Dish dish);
    List<PlatoAppResponseDto>PlatoAppResponseDtoList(List<Dish> dishes);

}
