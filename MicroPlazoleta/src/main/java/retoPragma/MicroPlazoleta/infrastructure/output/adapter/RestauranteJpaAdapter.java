package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.exception.RestaurantException.NoRetaurantExcepcion;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestauranteEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestauranteRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IPlatoRepository platoRepository;

    @Override
    public void saveRestaurante(Restaurante restaurante) {
        restauranteRepository.save(restauranteEntityMapper.toRestauranteEntity(restaurante));
    }

    @Override
    public Restaurante findRestauranteById(Long id) {
        return restauranteRepository.findById(id)
                .map(restauranteEntityMapper::toRestaurante)
                .orElseThrow(NoRetaurantExcepcion::new);
    }
    @Override
    public List<Restaurante> findAllRestaurantsOrderedByName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombreRestaurante").ascending());
        Page<RestauranteEntity> pageResult = restauranteRepository.findAll(pageable);

        return pageResult.getContent()
                .stream()
                .map(restauranteEntityMapper::toRestaurante)
                .collect(Collectors.toList());
    }

    @Override
    public boolean platoPerteneceARestaurante(Long idPlato, Long idRestaurante) {
        return platoRepository.existsByIdPlatoAndIdRestaurante(idPlato, idRestaurante);
    }
}
