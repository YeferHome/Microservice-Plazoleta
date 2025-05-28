package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantAppResponseMapper {
    RestaurantAppResponseDto toRestaurantAppResponseDto(Restaurant restaurant);
}
