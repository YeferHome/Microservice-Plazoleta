package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NoRetaurantExcepcion;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestauranteEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestauranteRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IPlatoRepository platoRepository;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restauranteRepository.save(restauranteEntityMapper.toRestauranteEntity(restaurant));
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restauranteRepository.findById(id)
                .map(restauranteEntityMapper::toRestaurante)
                .orElseThrow(NoRetaurantExcepcion::new);
    }
    @Override
    public List<Restaurant> findAllRestaurantsOrderedByName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombreRestaurante").ascending());
        Page<RestauranteEntity> pageResult = restauranteRepository.findAll(pageable);

        return pageResult.getContent()
                .stream()
                .map(restauranteEntityMapper::toRestaurante)
                .collect(Collectors.toList());
    }

    @Override
    public boolean platoBelongsRestaurant(Long idDish, Long idRestaurant) {
        return platoRepository.existsByIdPlatoAndIdRestaurante(idDish, idRestaurant);
    }
    @Override
    public boolean employeeBelongsRestaurant(Long idRestaurant) {
        return restauranteRepository.findById(idRestaurant).isPresent();
    }

}
