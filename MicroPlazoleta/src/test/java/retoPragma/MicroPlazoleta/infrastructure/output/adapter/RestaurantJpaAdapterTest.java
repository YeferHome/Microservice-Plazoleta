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
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestauranteEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestauranteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantJpaAdapterTest {

    private IRestauranteRepository restauranteRepository;
    private IRestauranteEntityMapper restauranteEntityMapper;
    private IPlatoRepository platoRepository;

    private RestaurantJpaAdapter restauranteJpaAdapter;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(IRestauranteRepository.class);
        restauranteEntityMapper = mock(IRestauranteEntityMapper.class);
        platoRepository = mock(IPlatoRepository.class);
        restauranteJpaAdapter = new RestaurantJpaAdapter(restauranteRepository, restauranteEntityMapper, platoRepository);
    }

    @Test
    void saveRestaurante() {
        Restaurant restaurant = mock(Restaurant.class);
        RestauranteEntity restauranteEntity = mock(RestauranteEntity.class);

        when(restauranteEntityMapper.toRestauranteEntity(restaurant)).thenReturn(restauranteEntity);

        restauranteJpaAdapter.saveRestaurant(restaurant);

        verify(restauranteEntityMapper).toRestauranteEntity(restaurant);
        verify(restauranteRepository).save(restauranteEntity);
    }

    @Test
    void findRestauranteById_success() {
        Long id = 1L;
        RestauranteEntity restauranteEntity = mock(RestauranteEntity.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restauranteRepository.findById(id)).thenReturn(Optional.of(restauranteEntity));
        when(restauranteEntityMapper.toRestaurante(restauranteEntity)).thenReturn(restaurant);

        Restaurant result = restauranteJpaAdapter.findRestaurantById(id);

        verify(restauranteRepository).findById(id);
        verify(restauranteEntityMapper).toRestaurante(restauranteEntity);
        assertEquals(restaurant, result);
    }

    @Test
    void findRestauranteById_notFound_throwsException() {
        Long id = 1L;

        when(restauranteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoRetaurantExcepcion.class, () -> restauranteJpaAdapter.findRestaurantById(id));

        verify(restauranteRepository).findById(id);
        verify(restauranteEntityMapper, never()).toRestaurante(any());
    }

    @Test
    void findAllRestaurantsOrderedByName() {
        RestauranteEntity restauranteEntity = mock(RestauranteEntity.class);
        Restaurant restaurant = mock(Restaurant.class);

        List<RestauranteEntity> entities = List.of(restauranteEntity);
        Page<RestauranteEntity> pageResult = new PageImpl<>(entities);

        when(restauranteRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
        when(restauranteEntityMapper.toRestaurante(restauranteEntity)).thenReturn(restaurant);

        PageRequestModel pageRequestModel = new PageRequestModel(0, 5);
        PageModel<Restaurant> result = restauranteJpaAdapter.findAllRestaurantsOrderedByName(pageRequestModel);

        verify(restauranteRepository).findAll(any(Pageable.class));
        verify(restauranteEntityMapper).toRestaurante(restauranteEntity);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(restaurant, result.getContent().get(0));
    }

    @Test
    void platoPerteneceARestaurante_true() {
        Long idPlato = 1L;
        Long idRestaurante = 10L;

        when(platoRepository.existsByIdPlatoAndIdRestaurante(idPlato, idRestaurante)).thenReturn(true);

        boolean result = restauranteJpaAdapter.platoBelongsRestaurant(idPlato, idRestaurante);

        verify(platoRepository).existsByIdPlatoAndIdRestaurante(idPlato, idRestaurante);
        assertTrue(result);
    }

    @Test
    void platoPerteneceARestaurante_false() {
        Long idPlato = 1L;
        Long idRestaurante = 10L;

        when(platoRepository.existsByIdPlatoAndIdRestaurante(idPlato, idRestaurante)).thenReturn(false);

        boolean result = restauranteJpaAdapter.platoBelongsRestaurant(idPlato, idRestaurante);

        verify(platoRepository).existsByIdPlatoAndIdRestaurante(idPlato, idRestaurante);
        assertFalse(result);
    }

    @Test
    void elEmpleadoPerteneceAlRestaurante_true() {
        Long restauranteId = 1L;

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(mock(RestauranteEntity.class)));

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
