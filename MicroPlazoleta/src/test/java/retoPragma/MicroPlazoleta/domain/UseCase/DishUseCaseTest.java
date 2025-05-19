package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Mock
    private IDishPersistencePort platoPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantePersistencePort;
    @Mock
    private IUserServicePort usuarioServicePort;

    private DishUseCase DIshUseCase;

    private Dish dish;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        DIshUseCase = new DishUseCase(platoPersistencePort, restaurantePersistencePort, usuarioServicePort);

        dish = new Dish(1L, "Plato1", "Delicioso", 10000L, "url.com", "Categoría", true, 1L, 1L);
        restaurant = new Restaurant();
        restaurant.setIdUsuario(1L);
    }

    @Test
    void savePlato_success() {
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(restaurant);

        DIshUseCase.saveDish(dish);

        assertTrue(dish.getEstate());
        verify(platoPersistencePort).saveDish(dish);
    }

    @Test
    void savePlato_restauranteNotFound_throwsException() {
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(null);

        assertThrows(PlatoAssociatedException.class, () -> DIshUseCase.saveDish(dish));
    }

    @Test
    void savePlato_usuarioIncorrecto_throwsException() {
        Restaurant otroRestaurant = new Restaurant();
        otroRestaurant.setIdUsuario(999L);
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(otroRestaurant);

        assertThrows(PlatoOwnerException.class, () -> DIshUseCase.saveDish(dish));
    }

    @Test
    void savePlato_precioInvalido_throwsException() {
        dish.setPriceDish(0L);
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(restaurant);

        assertThrows(PricePlatoException.class, () -> DIshUseCase.saveDish(dish));
    }

    @Test
    void updatePlato_success() {
        when(usuarioServicePort.obtainRolUser(1L)).thenReturn("PROPIETARIO");
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(restaurant);
        when(platoPersistencePort.findDishById(1L)).thenReturn(dish);

        Dish modificado = new Dish(1L, "Plato mod", "Modificado", 12000L, "url.com", "Categoría", true, 1L, 1L);

        Dish actualizado = DIshUseCase.updateDish(1L, modificado, 1L);

        assertEquals("Modificado", actualizado.getDescriptionDish());
        assertEquals(12000L, actualizado.getPriceDrish());
        verify(platoPersistencePort).saveDish(actualizado);
    }

    @Test
    void updatePlato_usuarioSinRol_throwsException() {
        when(usuarioServicePort.obtainRolUser(1L)).thenReturn("CLIENTE");

        assertThrows(NoPermissionCreateException.class, () -> DIshUseCase.updateDish(1L, dish, 1L));
    }

    @Test
    void updatePlato_usuarioNoPropietarioDelRestaurante_throwsException() {
        Restaurant restaurantOtroUsuario = new Restaurant();
        restaurantOtroUsuario.setIdUsuario(999L);
        when(usuarioServicePort.obtainRolUser(1L)).thenReturn("PROPIETARIO");
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(restaurantOtroUsuario);

        assertThrows(OwnerNoRestaurantException.class, () -> DIshUseCase.updateDish(1L, dish, 1L));
    }

    @Test
    void updatePlato_platoNoExiste_throwsException() {
        when(usuarioServicePort.obtainRolUser(anyLong())).thenReturn("PROPIETARIO");
        when(restaurantePersistencePort.findRestaurantById(anyLong())).thenReturn(restaurant);
        when(platoPersistencePort.findDishById(anyLong())).thenReturn(null);

        assertThrows(ExistecePlatoException.class, () -> DIshUseCase.updateDish(1L, dish, 1L));
    }

    @Test
    void updateEstadoPlato_success() {
        Dish dishExistente = new Dish(1L, "Plato1", "Desc", 10000L, "url.com", "cat", true, 1L, 1L);
        Restaurant restaurantPropietario = new Restaurant();
        restaurantPropietario.setIdUsuario(1L);

        when(platoPersistencePort.findDishById(1L)).thenReturn(dishExistente);
        when(usuarioServicePort.obtainRolUser(1L)).thenReturn("PROPIETARIO");
        when(restaurantePersistencePort.findRestaurantById(1L)).thenReturn(restaurantPropietario);

        Dish result = DIshUseCase.updateEstateDish(1L, false, 1L);

        assertNotNull(result);
        assertFalse(result.getEstate());
        verify(platoPersistencePort).saveDish(dishExistente);
    }

    @Test
    void getPlatosByRestauranteWithCategoria() {
        when(platoPersistencePort.findByRestaurantAndCategory(1L, "cat", 0, 10))
                .thenReturn(List.of(dish));

        List<Dish> result = DIshUseCase.getDishByRestaurant(1L, "cat", 0, 10);

        assertEquals(1, result.size());
        verify(platoPersistencePort).findByRestaurantAndCategory(1L, "cat", 0, 10);
    }

    @Test
    void getPlatosByRestauranteWithoutCategoria() {
        when(platoPersistencePort.findByRestaurant(1L, 0, 10))
                .thenReturn(List.of(dish));

        List<Dish> result = DIshUseCase.getDishByRestaurant(1L, "", 0, 10);

        assertEquals(1, result.size());
        verify(platoPersistencePort).findByRestaurant(1L, 0, 10);
    }
}
