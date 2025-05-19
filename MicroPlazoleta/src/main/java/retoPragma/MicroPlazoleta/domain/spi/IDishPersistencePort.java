package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    Dish findDishById(Long idPlato);
    Dish updateEstateDish(Long idPlato, boolean nuevoEstado, Long idUsuario);
    List<Dish> findByRestaurantAndCategory(Long idRestaurante, String categoria, int page, int size);
    List<Dish> findByRestaurant(Long idRestaurante, int page, int size);
}
