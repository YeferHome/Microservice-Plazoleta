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

        private final IUserServicePort usuarioServicePort;
        private final IRestaurantPersistencePort restaurantePersistencePort;
        private final IDishPersistencePort platoPersistencePort;


    public DishValidationUtil(IUserServicePort usuarioServicePort, IRestaurantPersistencePort restaurantePersistencePort, IDishPersistencePort platoPersistencePort) {
        this.usuarioServicePort = usuarioServicePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
    }

    public void validateDish(Dish dish) {

            Restaurant restaurant = restaurantePersistencePort.findRestaurantById(dish.getIdRestaurant());
            if (restaurant == null) {
                throw new PlatoAssociatedException();
            }

            if (!restaurant.getIdUsuario().equals(dish.getIdUser())) {
                throw new PlatoOwnerException();
            }

            if (dish.getPriceDrish() <= PRECIO_MINIMO) {
                throw new PricePlatoException();
            }
        }

    public void validateDishExistent(Long idPlato) {
        Dish dish = platoPersistencePort.findDishById(idPlato);
        if (dish == null) {
            throw new ExistecePlatoException();
        }
    }

    public void validateOwner(Dish dish, Long idUsuarioToken) {

        String rolUsuario = usuarioServicePort.obtainRolUser(dish.getIdUser());
        if (!PROPIETARIO.equalsIgnoreCase(rolUsuario)) {
            throw new NoPermissionCreateException();
        }
        Restaurant restaurant = restaurantePersistencePort.findRestaurantById(dish.getIdRestaurant());
        if (!restaurant.getIdUsuario().equals(idUsuarioToken)) {
            throw new OwnerNoRestaurantException();
        }
    }

}
