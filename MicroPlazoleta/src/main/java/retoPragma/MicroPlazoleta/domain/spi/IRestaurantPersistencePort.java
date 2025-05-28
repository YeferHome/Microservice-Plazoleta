package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);
    Restaurant findRestaurantById(Long id);
    PageModel<Restaurant> findAllRestaurantsOrderedByName(PageRequestModel pageRequestModel);
    boolean platoBelongsRestaurant(Long idDish, Long idRestaurant);
    boolean employeeBelongsRestaurant(Long idRestaurant);
}
