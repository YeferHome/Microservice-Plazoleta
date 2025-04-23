package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Restaurante;

public interface IRestaurantePersistencePort {

    void saveRestaurante(Restaurante restaurante);
    Restaurante findRestauranteById(Long id);
}
