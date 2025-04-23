package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Restaurante;

import java.util.Optional;

public interface IRestauranteServicePort {

    void saveRestaurante(Restaurante restaurante);

    Optional<Restaurante> findRestauranteById(Long id);
}
