package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    private IRestaurantPersistencePort restaurantPersistencePort;
    private IUserServicePort userServicePort;
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantPersistencePort = mock(IRestaurantPersistencePort.class);
        userServicePort = mock(IUserServicePort.class);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userServicePort);
    }

    @Test
    void saveRestaurant_Success() {
        Restaurant restaurant = new Restaurant(1L, "Mi Restaurante", 123456789L, "Calle 123", "+573001112233", "logo.png", 10L);
        when(userServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_ThrowsNoOwnerException() {
        Restaurant restaurant = new Restaurant(1L, "Mi Restaurante", 123456789L, "Calle 123", "+573001112233", "logo.png", 10L);
        when(userServicePort.obtainRolUser(10L)).thenReturn("CLIENTE");

        assertThrows(NoOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurant_ThrowsDocumentException() {
        Restaurant restaurant = new Restaurant(1L, "Mi Restaurante", -50L, "Calle 123", "+573001112233", "logo.png", 10L);
        when(userServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        assertThrows(DocumentException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurant_ThrowsPhoneException() {
        Restaurant restaurant = new Restaurant(1L, "Mi Restaurante", 123456789L, "Calle 123", "123ABC", "logo.png", 10L);
        when(userServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        assertThrows(PhoneException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurant_ThrowsNameRestaurantException() {
        Restaurant restaurant = new Restaurant(1L, "12345", 123456789L, "Calle 123", "+573001112233", "logo.png", 10L);
        when(userServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        assertThrows(NameRestaurantException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void getAllRestaurants_ReturnsPageModel() {
        PageRequestModel pageRequestModel = new PageRequestModel(0, 2);
        List<Restaurant> restaurantList = List.of(
                new Restaurant(1L, "Resto1", 123L, "Dir1", "+573000000001", "logo1.png", 1L),
                new Restaurant(2L, "Resto2", 456L, "Dir2", "+573000000002", "logo2.png", 2L)
        );

        PageModel<Restaurant> pageModel = new PageModel<>(restaurantList, 0, 2, 2L);

        when(restaurantPersistencePort.findAllRestaurantsOrderedByName(pageRequestModel)).thenReturn(pageModel);

        PageModel<Restaurant> result = restaurantUseCase.getAllRestaurants(pageRequestModel);

        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPageNumber());
        assertEquals(2, result.getPageSize());
        assertEquals(2L, result.getTotalElements());
        assertEquals(restaurantList, result.getContent());

        verify(restaurantPersistencePort).findAllRestaurantsOrderedByName(pageRequestModel);
    }
}
