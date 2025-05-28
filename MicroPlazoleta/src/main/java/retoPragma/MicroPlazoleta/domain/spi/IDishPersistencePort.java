package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;

public interface IDishPersistencePort {

    void saveDish(Dish dish);

    Dish findDishById(Long idDish);

    Dish updateEstateDish(Long idDish, boolean newState, Long idUser);

    PageModel<Dish> findDishesByRestaurantAndOptionalCategory(Long idRestaurant, String category, PageRequestModel pageRequestModel);
}
