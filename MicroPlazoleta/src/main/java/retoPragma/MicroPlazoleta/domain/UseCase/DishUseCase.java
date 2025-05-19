package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IDishServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.util.platoUtil.DishValidationUtil;

import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final DishValidationUtil dishValidationUtil;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.dishValidationUtil = new DishValidationUtil(userServicePort, restaurantPersistencePort, dishPersistencePort);
    }

    @Override
    public void saveDish(Dish dish) {

        dishValidationUtil.validateDish(dish);
        dish.setEstate(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish updateDish(Long idDish, Dish dishModified, Long idUser) {

        dishValidationUtil.validateOwner(dishModified, idUser);
        dishValidationUtil.validateDish(dishModified);
        dishValidationUtil.validateDishExistent(idDish);

        Dish dish = dishPersistencePort.findDishById(idDish);

        if (dish == null) {
            throw new ExistecePlatoException();
        }

        dish.setDescriptionDish(dishModified.getDescriptionDish());
        dish.setPriceDish(dishModified.getPriceDrish());
        dishPersistencePort.saveDish(dish);
        return dish;
    }

    @Override
    public Dish updateEstateDish(Long idDish, boolean newEstate, Long idUser) {

        dishValidationUtil.validateDishExistent(idDish);
        dishValidationUtil.validateOwner(dishPersistencePort.findDishById(idDish), idUser);

        Dish dish = dishPersistencePort.findDishById(idDish);
        dish.setEstate(newEstate);
        dishPersistencePort.saveDish(dish);
        return dish;
    }

    @Override
    public List<Dish> getDishByRestaurant(Long idRestaurant, String category, int page, int size) {

        if (category != null && !category.trim().isEmpty()) {
            return dishPersistencePort.findByRestaurantAndCategory(idRestaurant, category, page, size);
        } else {
            return dishPersistencePort.findByRestaurant(idRestaurant, page, size);
        }
    }
}
