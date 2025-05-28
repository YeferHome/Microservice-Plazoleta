package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantSummaryResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantAppRequestMapper {
    Restaurant toRestaurant(RestaurantAppRequestDto restaurantAppRequestDto);
    RestaurantSummaryResponseDto toRestauranteAppResponseDto(Restaurant restaurant);
}
