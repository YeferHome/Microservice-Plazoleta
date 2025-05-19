package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);
    Restaurant findRestaurantById(Long id);
    List<Restaurant> findAllRestaurantsOrderedByName(int page, int size);
    boolean platoBelongsRestaurant(Long idDish, Long idRestaurant);
    boolean employeeBelongsRestaurant(Long idRestaurant);
}
