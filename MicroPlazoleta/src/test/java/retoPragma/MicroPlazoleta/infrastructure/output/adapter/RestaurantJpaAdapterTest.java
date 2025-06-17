package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NoRetaurantExcepcion;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestaurantEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IDishRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestaurantRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantJpaAdapterTest {

    private IRestaurantRepository restauranteRepository;
    private IRestauranteEntityMapper restauranteEntityMapper;
    private IDishRepository platoRepository;

    private RestaurantJpaAdapter restauranteJpaAdapter;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(IRestaurantRepository.class);
        restauranteEntityMapper = mock(IRestauranteEntityMapper.class);
        platoRepository = mock(IDishRepository.class);
        restauranteJpaAdapter = new RestaurantJpaAdapter(restauranteRepository, restauranteEntityMapper, platoRepository);
    }

    @Test
    void saveRestaurante() {
        Restaurant restaurant = mock(Restaurant.class);
        RestaurantEntity restaurantEntity = mock(RestaurantEntity.class);

        when(restauranteEntityMapper.toRestaurantEntity(restaurant)).thenReturn(restaurantEntity);

        restauranteJpaAdapter.saveRestaurant(restaurant);

        verify(restauranteEntityMapper).toRestaurantEntity(restaurant);
        verify(restauranteRepository).save(restaurantEntity);
    }

    @Test
    void findRestauranteById_success() {
        Long id = 1L;
        RestaurantEntity restaurantEntity = mock(RestaurantEntity.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restaurantEntity));
        when(restauranteEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        Restaurant result = restauranteJpaAdapter.findRestaurantById(id);

        verify(restauranteRepository).findById(id);
        verify(restauranteEntityMapper).toRestaurant(restaurantEntity);
        assertEquals(restaurant, result);
    }

    @Test
    void findRestauranteById_notFound_throwsException() {
        Long id = 1L;

        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoRetaurantExcepcion.class, () -> restauranteJpaAdapter.findRestaurantById(id));

        verify(restauranteRepository).findById(id);
        verify(restauranteEntityMapper, never()).toRestaurant(any());
    }

    @Test
    void findAllRestaurantsOrderedByName() {
        RestaurantEntity restaurantEntity = mock(RestaurantEntity.class);
        Restaurant restaurant = mock(Restaurant.class);

        List<RestaurantEntity> entities = List.of(restaurantEntity);
        Page<RestaurantEntity> pageResult = new PageImpl<>(entities);

        when(restauranteRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
        when(restauranteEntityMapper.toRestaurant(restaurantEntity)).thenReturn(restaurant);

        PageRequestModel pageRequestModel = new PageRequestModel(0, 5);
        PageModel<Restaurant> result = restauranteJpaAdapter.findAllRestaurantsOrderedByName(pageRequestModel);

        verify(restauranteRepository).findAll(any(Pageable.class));
        verify(restauranteEntityMapper).toRestaurant(restaurantEntity);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(restaurant, result.getContent().get(0));
    }

    @Test
    void platoPerteneceARestaurante_true() {
        Long idPlato = 1L;
        Long idRestaurante = 10L;

        when(platoRepository.existsByIdDishAndIdRestaurant(idPlato, idRestaurante)).thenReturn(true);

        boolean result = restauranteJpaAdapter.platoBelongsRestaurant(idPlato, idRestaurante);

        verify(platoRepository).existsByIdDishAndIdRestaurant(idPlato, idRestaurante);
        assertTrue(result);
    }

    @Test
    void platoPerteneceARestaurante_false() {
        Long idPlato = 1L;
        Long idRestaurante = 10L;

        when(platoRepository.existsByIdDishAndIdRestaurant(idPlato, idRestaurante)).thenReturn(false);

        boolean result = restauranteJpaAdapter.platoBelongsRestaurant(idPlato, idRestaurante);

        verify(platoRepository).existsByIdDishAndIdRestaurant(idPlato, idRestaurante);
        assertFalse(result);
    }

    @Test
    void elEmpleadoPerteneceAlRestaurante_true() {
        Long restauranteId = 1L;

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(mock(RestaurantEntity.class)));

        boolean result = restauranteJpaAdapter.employeeBelongsRestaurant(restauranteId);

        verify(restauranteRepository).findById(restauranteId);
        assertTrue(result);
    }

    @Test
    void elEmpleadoPerteneceAlRestaurante_false() {
        Long restauranteId = 1L;

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.empty());

        boolean result = restauranteJpaAdapter.employeeBelongsRestaurant(restauranteId);

        verify(restauranteRepository).findById(restauranteId);
        assertFalse(result);
    }
}
