package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NoRetaurantExcepcion;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestauranteEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestauranteRepository;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestauranteRepository restaurantRepository;
    private final IRestauranteEntityMapper restaurantEntityMapper;
    private final IPlatoRepository platoRepository;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toRestauranteEntity(restaurant));
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::toRestaurante)
                .orElseThrow(NoRetaurantExcepcion::new);
    }

    @Override
    public PageModel<Restaurant> findAllRestaurantsOrderedByName(PageRequestModel pageRequestModel) {
        Pageable pageable = PageRequest.of(
                pageRequestModel.getPage(),
                pageRequestModel.getSize(),
                Sort.by("nameRestaurant").ascending()
        );

        Page<RestauranteEntity> pageResult = restaurantRepository.findAll(pageable);

        List<Restaurant> content = pageResult.getContent().stream()
                .map(restaurantEntityMapper::toRestaurante)
                .toList();

        return new PageModel<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements()
        );
    }

    @Override
    public boolean platoBelongsRestaurant(Long idDish, Long idRestaurant) {
        return platoRepository.existsByIdPlatoAndIdRestaurante(idDish, idRestaurant);
    }

    @Override
    public boolean employeeBelongsRestaurant(Long idRestaurant) {
        return restaurantRepository.findById(idRestaurant).isPresent();
    }
}
