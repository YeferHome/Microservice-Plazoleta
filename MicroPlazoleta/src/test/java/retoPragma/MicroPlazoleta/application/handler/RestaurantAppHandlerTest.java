package retoPragma.MicroPlazoleta.application.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestauranteResumenResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IRestauranteAppRequestMapper;
import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantAppHandlerTest {

    private IRestauranteServicePort restauranteServicePort;
    private IRestauranteAppRequestMapper restauranteAppRequestMapper;
    private RestauranteAppHandler restauranteAppHandler;

    @BeforeEach
    void setUp() {
        restauranteServicePort = mock(IRestauranteServicePort.class);
        restauranteAppRequestMapper = mock(IRestauranteAppRequestMapper.class);
        restauranteAppHandler = new RestauranteAppHandler(restauranteServicePort, restauranteAppRequestMapper);
    }

    @Test
    void saveRestauranteInRestauranteApp() {
        // Arrange
        RestauranteAppRequestDto requestDto = new RestauranteAppRequestDto();
        Restaurant restaurant = new Restaurant();

        when(restauranteAppRequestMapper.toRestaurante(requestDto)).thenReturn(restaurant);

        // Act
        restauranteAppHandler.saveRestauranteInRestauranteApp(requestDto);

        // Assert
        verify(restauranteAppRequestMapper, times(1)).toRestaurante(requestDto);
        verify(restauranteServicePort, times(1)).saveRestaurante(restaurant);
    }

    @Test
    void listRestaurantes() {
        // Arrange
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setNombreRestaurante("Restaurante 1");
        restaurant1.setUrlLogo("http://logo1.png");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setNombreRestaurante("Restaurante 2");
        restaurant2.setUrlLogo("http://logo2.png");

        List<Restaurant> mockRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(restauranteServicePort.getAllRestaurantes(0, 10)).thenReturn(mockRestaurants);

        // Act
        List<RestauranteResumenResponseDto> result = restauranteAppHandler.listRestaurantes(0, 10);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Restaurante 1", result.get(0).getNombreRestaurante());
        assertEquals("http://logo1.png", result.get(0).getUrlLogo());
        assertEquals("Restaurante 2", result.get(1).getNombreRestaurante());
        assertEquals("http://logo2.png", result.get(1).getUrlLogo());

        verify(restauranteServicePort, times(1)).getAllRestaurantes(0, 10);
    }
}
