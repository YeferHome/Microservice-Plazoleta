package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);
    Dish updateDish(Long idDish, Dish dishModified, Long idUser);
    Dish updateEstateDish(Long idDish, boolean newEstate, Long idUser);
    List<Dish> getDishByRestaurant(Long idRestaurant, String category, int page, int size);
}