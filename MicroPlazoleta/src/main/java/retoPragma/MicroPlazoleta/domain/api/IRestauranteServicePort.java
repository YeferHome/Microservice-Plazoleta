package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Restaurant;

import java.util.List;

public interface IRestauranteServicePort {

    void saveRestaurante(Restaurant restaurant);
    List<Restaurant> getAllRestaurantes(int page, int size);
}
