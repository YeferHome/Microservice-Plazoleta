package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IRestaurantServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RestaurantValidationUtil;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort userServicePort;
    private final RestaurantValidationUtil restaurantValidationUtil;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
        this.restaurantValidationUtil = new RestaurantValidationUtil(restaurantPersistencePort, userServicePort);
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantValidationUtil.validateRestaurant(restaurant);
        restaurantPersistencePort.saveRestaurant(restaurant);

        Restaurant savedRestaurant = restaurantPersistencePort.findLastInsertedByUserId(restaurant.getIdUser());

        userServicePort.assignRestaurantToUser(
                restaurant.getIdUser(),
                savedRestaurant.getIdRestaurant()
        );
    }

    @Override
    public PageModel<Restaurant> getAllRestaurants(PageRequestModel pageRequestModel) {
        return restaurantPersistencePort.findAllRestaurantsOrderedByName(pageRequestModel);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantPersistencePort.findRestaurantById(id);
    }
    @Override
    public Restaurant findRestaurantByUserId(Long userId) {
        return restaurantPersistencePort.findByUserId(userId);
    }
    @Override
    public Restaurant findByUserId(Long idUser) {
        return restaurantPersistencePort.findLastInsertedByUserId(idUser);
    }

    @Override
    public boolean existsRestaurantById(Long restaurantId) {
        return restaurantPersistencePort.existsById(restaurantId);
    }


}
