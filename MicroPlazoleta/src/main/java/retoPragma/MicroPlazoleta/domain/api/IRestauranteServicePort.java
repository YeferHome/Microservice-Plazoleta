package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface IRestauranteServicePort {

    void saveRestaurante(Restaurante restaurante);
    List<Restaurante> getAllRestaurantes(int page, int size);
}
