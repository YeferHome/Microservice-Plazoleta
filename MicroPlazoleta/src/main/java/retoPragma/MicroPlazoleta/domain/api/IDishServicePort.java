package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;

public interface IDishServicePort {

    void saveDish(Dish dish);

    Dish updateDish(Long idDish, Dish dishModified, Long idUser);

    Dish updateEstateDish(Long idDish, boolean newEstate, Long idUser);

    PageModel<Dish> getDishesByRestaurantAndOptionalCategory(Long idRestaurant, String category, PageRequestModel pageRequestModel);
}
