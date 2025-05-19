package retoPragma.MicroPlazoleta.domain.UseCase;


import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RestaurantValidationUtil;

import java.util.List;

public class RestaurantUseCase implements IRestauranteServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final RestaurantValidationUtil restaurantValidationUtil;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantValidationUtil = new RestaurantValidationUtil(restaurantPersistencePort, userServicePort);
    }

    @Override
    public void saveRestaurante(Restaurant restaurant) {
        restaurantValidationUtil.validateRestaurant(restaurant);
        restaurantPersistencePort.saveRestaurant(restaurant);
    }
    
    @Override
    public List<Restaurant> getAllRestaurantes(int page, int size) {
        return restaurantPersistencePort.findAllRestaurantsOrderedByName(page, size);
    }




}