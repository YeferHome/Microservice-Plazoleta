package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import retoPragma.MicroPlazoleta.application.dto.DishAppResponseDto;
import retoPragma.MicroPlazoleta.application.dto.DishUpdateEstateRequestDto;
import retoPragma.MicroPlazoleta.application.dto.DishUpdateResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Dish;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoAppResponseMapper {

    DishAppResponseDto toDishAppResponseDto(Dish dish);
    DishUpdateResponseDto toDishUpdateResponseDto(Dish dish);
    DishUpdateEstateRequestDto toDishUpdateEstateRequestDto(Dish dish);
    List<DishAppResponseDto> DishAppResponseDtoList(List<Dish> dishes);

}
