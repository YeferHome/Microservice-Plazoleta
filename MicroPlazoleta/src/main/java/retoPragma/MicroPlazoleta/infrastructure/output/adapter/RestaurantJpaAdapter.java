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
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestaurantEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IDishRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestaurantRepository;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestauranteEntityMapper restaurantEntityMapper;
    private final IDishRepository platoRepository;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::toRestaurant)
                .orElseThrow(NoRetaurantExcepcion::new);
    }

    @Override
    public PageModel<Restaurant> findAllRestaurantsOrderedByName(PageRequestModel pageRequestModel) {
        Pageable pageable = PageRequest.of(
                pageRequestModel.getPage(),
                pageRequestModel.getSize(),
                Sort.by("nameRestaurant").ascending()
        );

        Page<RestaurantEntity> pageResult = restaurantRepository.findAll(pageable);

        List<Restaurant> content = pageResult.getContent().stream()
                .map(restaurantEntityMapper::toRestaurant)
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
        return platoRepository.existsByIdDishAndIdRestaurant(idDish, idRestaurant);
    }

    @Override
    public boolean employeeBelongsRestaurant(Long idRestaurant) {
        return restaurantRepository.findById(idRestaurant).isPresent();
    }

    @Override
    public Restaurant findLastInsertedByUserId(Long idUser) {
        return restaurantRepository
                .findTopByIdUserOrderByIdRestaurantDesc(idUser)
                .map(restaurantEntityMapper::toRestaurant)
                .orElseThrow(() -> new RuntimeException("No se encontró un restaurante para el usuario con id: " + idUser));
    }

    @Override
    public Restaurant findByUserId(Long idUser) {
        return restaurantRepository
                .findByIdUser(idUser)
                .map(restaurantEntityMapper::toRestaurant)
                .orElseThrow(() -> new RuntimeException("No se encontró restaurante para el usuario con id: " + idUser));
    }
    @Override
    public boolean existsById(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }


}
