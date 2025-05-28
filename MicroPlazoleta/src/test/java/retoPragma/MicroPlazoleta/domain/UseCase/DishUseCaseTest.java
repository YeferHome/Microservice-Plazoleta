package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Mock private IDishPersistencePort dishPersistencePort;
    @Mock private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock private IUserServicePort userServicePort;

    private DishUseCase dishUseCase;

    private Dish dish;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantPersistencePort, userServicePort);
        dish = new Dish(1L, "Plato1", "Delicioso", 10000L, "url.com", "Categoría", true, 1L, 1L);
        restaurant = new Restaurant();
        restaurant.setIdUser(1L);
    }

    @Test
    void saveDish_success() {
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(restaurant);
        dishUseCase.saveDish(dish);
        assertTrue(dish.getEstate());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void saveDish_restaurantNotFound_throwsException() {
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(null);
        assertThrows(PlatoAssociatedException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void saveDish_invalidOwner_throwsException() {
        Restaurant other = new Restaurant();
        other.setIdUser(999L);
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(other);
        assertThrows(PlatoOwnerException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void saveDish_invalidPrice_throwsException() {
        dish.setPriceDish(0L);
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(restaurant);
        assertThrows(PricePlatoException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void updateDish_success() {
        when(userServicePort.obtainRolUser(1L)).thenReturn("PROPIETARIO");
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(restaurant);
        when(dishPersistencePort.findDishById(1L)).thenReturn(dish);

        Dish updated = new Dish(1L, "Nuevo", "Cambiado", 12000L, "url.com", "cat", true, 1L, 1L);

        Dish result = dishUseCase.updateDish(1L, updated, 1L);

        assertEquals("Cambiado", result.getDescriptionDish());
        assertEquals(12000L, result.getPriceDrish());
        verify(dishPersistencePort).saveDish(result);
    }

    @Test
    void updateDish_userNotOwner_throwsException() {
        when(userServicePort.obtainRolUser(1L)).thenReturn("CLIENTE");
        assertThrows(NoPermissionCreateException.class, () -> dishUseCase.updateDish(1L, dish, 1L));
    }

    @Test
    void updateDish_notRestaurantOwner_throwsException() {
        Restaurant other = new Restaurant();
        other.setIdUser(999L);
        when(userServicePort.obtainRolUser(1L)).thenReturn("PROPIETARIO");
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(other);
        assertThrows(OwnerNoRestaurantException.class, () -> dishUseCase.updateDish(1L, dish, 1L));
    }

    @Test
    void updateDish_dishNotExists_throwsException() {
        when(userServicePort.obtainRolUser(anyLong())).thenReturn("PROPIETARIO");
        when(restaurantPersistencePort.findRestaurantById(anyLong())).thenReturn(restaurant);
        when(dishPersistencePort.findDishById(anyLong())).thenReturn(null);
        assertThrows(ExistecePlatoException.class, () -> dishUseCase.updateDish(1L, dish, 1L));
    }

    @Test
    void updateDishEstate_success() {
        Dish existing = new Dish(1L, "Plato", "desc", 10000L, "url", "cat", true, 1L, 1L);
        when(dishPersistencePort.findDishById(1L)).thenReturn(existing);
        when(userServicePort.obtainRolUser(1L)).thenReturn("PROPIETARIO");
        when(restaurantPersistencePort.findRestaurantById(1L)).thenReturn(restaurant);

        Dish result = dishUseCase.updateEstateDish(1L, false, 1L);

        assertFalse(result.getEstate());
        verify(dishPersistencePort).saveDish(existing);
    }

    @Test
    void getDishesByRestaurantAndOptionalCategory_success() {
        PageRequestModel pageRequest = new PageRequestModel(0, 10);
        PageModel<Dish> expected = new PageModel<>(List.of(dish), 0, 10, 1);


        when(dishPersistencePort.findDishesByRestaurantAndOptionalCategory(1L, "cat", pageRequest))
                .thenReturn(expected);

        PageModel<Dish> result = dishUseCase.getDishesByRestaurantAndOptionalCategory(1L, "cat", pageRequest);

        assertEquals(expected, result);
        verify(dishPersistencePort).findDishesByRestaurantAndOptionalCategory(1L, "cat", pageRequest);
    }
}
