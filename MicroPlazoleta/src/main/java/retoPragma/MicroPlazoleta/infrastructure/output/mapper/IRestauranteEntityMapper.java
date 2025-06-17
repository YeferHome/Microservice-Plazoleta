package retoPragma.MicroPlazoleta.infrastructure.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestaurantEntity;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestauranteEntityMapper {

    RestaurantEntity toRestaurantEntity(Restaurant restaurant);
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);
}