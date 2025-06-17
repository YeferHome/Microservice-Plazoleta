package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.application.dto.RestaurantIdResponseDto;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);
    PageModel<Restaurant> getAllRestaurants(PageRequestModel pageRequestModel);
    Restaurant findRestaurantById(Long id);
    Restaurant findRestaurantByUserId(Long userId);
    Restaurant findByUserId(Long idUser);
    boolean existsRestaurantById(Long restaurantId);


}
