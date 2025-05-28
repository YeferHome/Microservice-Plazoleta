package retoPragma.MicroPlazoleta.domain.util.platoUtil;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.*;

import static retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoConstants.PRECIO_MINIMO;
import static retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoConstants.PROPIETARIO;


public class DishValidationUtil {

        private final IUserServicePort userServicePort;
        private final IRestaurantPersistencePort restaurantPersistencePort;
        private final IDishPersistencePort dishPersistencePort;


    public DishValidationUtil(IUserServicePort userServicePort, IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort) {
        this.userServicePort = userServicePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }

    public void validateDish(Dish dish) {

            Restaurant restaurant = restaurantPersistencePort.findRestaurantById(dish.getIdRestaurant());
            if (restaurant == null) {
                throw new PlatoAssociatedException();
            }

            if (!restaurant.getIdUser().equals(dish.getIdUser())) {
                throw new PlatoOwnerException();
            }

            if (dish.getPriceDrish() <= PRECIO_MINIMO) {
                throw new PricePlatoException();
            }
        }

    public void validateDishExistent(Long idPlato) {
        Dish dish = dishPersistencePort.findDishById(idPlato);
        if (dish == null) {
            throw new ExistecePlatoException();
        }
    }

    public void validateOwner(Dish dish, Long idUserToken) {

        String rolUser = userServicePort.obtainRolUser(dish.getIdUser());
        if (!PROPIETARIO.equalsIgnoreCase(rolUser)) {
            throw new NoPermissionCreateException();
        }
        Restaurant restaurant = restaurantPersistencePort.findRestaurantById(dish.getIdRestaurant());
        if (!restaurant.getIdUser().equals(idUserToken)) {
            throw new OwnerNoRestaurantException();
        }
    }

}
