package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.DocumentException;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NameRestaurantException;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NoOwnerException;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.PhoneException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    private IRestaurantPersistencePort restaurantePersistencePort;
    private IUserServicePort usuarioServicePort;
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantePersistencePort = mock(IRestaurantPersistencePort.class);
        usuarioServicePort = mock(IUserServicePort.class);
        restaurantUseCase = new RestaurantUseCase(restaurantePersistencePort, usuarioServicePort);
    }

    @Test
    void saveRestaurante_Success() {
        Restaurant restaurant = new Restaurant(
                1L, "Mi Restaurante", 123456789L, "Calle 123", "+573001112233", "logo.png", 10L
        );

        when(usuarioServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        restaurantUseCase.saveRestaurante(restaurant);

        verify(restaurantePersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurante_ThrowsNoOwnerException() {
        Restaurant restaurant = new Restaurant(
                1L, "Mi Restaurante", 123456789L, "Calle 123", "+573001112233", "logo.png", 10L
        );

        when(usuarioServicePort.obtainRolUser(10L)).thenReturn("CLIENTE");

        assertThrows(NoOwnerException.class, () -> restaurantUseCase.saveRestaurante(restaurant));
        verify(restaurantePersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurante_ThrowsDocumentException() {
        Restaurant restaurant = new Restaurant(
                1L, "Mi Restaurante", -50L, "Calle 123", "+573001112233", "logo.png", 10L
        );

        when(usuarioServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        assertThrows(DocumentException.class, () -> restaurantUseCase.saveRestaurante(restaurant));
        verify(restaurantePersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurante_ThrowsPhoneException() {
        Restaurant restaurant = new Restaurant(
                1L, "Mi Restaurante", 123456789L, "Calle 123", "123ABC", "logo.png", 10L
        );

        when(usuarioServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        assertThrows(PhoneException.class, () -> restaurantUseCase.saveRestaurante(restaurant));
        verify(restaurantePersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurante_ThrowsNameRestaurantException() {
        Restaurant restaurant = new Restaurant(
                1L, "12345", 123456789L, "Calle 123", "+573001112233", "logo.png", 10L
        );

        when(usuarioServicePort.obtainRolUser(10L)).thenReturn("PROPIETARIO");

        assertThrows(NameRestaurantException.class, () -> restaurantUseCase.saveRestaurante(restaurant));
        verify(restaurantePersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void getAllRestaurantes_ReturnsList() {
        List<Restaurant> expectedList = List.of(
                new Restaurant(1L, "Resto1", 123L, "Dir1", "+573000000001", "logo1.png", 1L),
                new Restaurant(2L, "Resto2", 456L, "Dir2", "+573000000002", "logo2.png", 2L)
        );

        when(restaurantePersistencePort.findAllRestaurantsOrderedByName(0, 2)).thenReturn(expectedList);

        List<Restaurant> actualList = restaurantUseCase.getAllRestaurantes(0, 2);

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList, actualList);
        verify(restaurantePersistencePort, times(1)).findAllRestaurantsOrderedByName(0, 2);
    }
}
