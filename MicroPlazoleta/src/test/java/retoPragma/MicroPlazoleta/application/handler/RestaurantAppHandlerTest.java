package retoPragma.MicroPlazoleta.application.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantIdResponseDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantSummaryResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IRestaurantAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IRestaurantAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IRestaurantServicePort;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantAppHandlerTest {

    private IRestaurantServicePort restaurantServicePort;
    private IRestaurantAppRequestMapper restaurantAppRequestMapper;
    private IRestaurantAppResponseMapper restaurantAppResponseMapper;
    private RestaurantAppHandler restaurantAppHandler;

    @BeforeEach
    void setUp() {
        restaurantServicePort = mock(IRestaurantServicePort.class);
        restaurantAppRequestMapper = mock(IRestaurantAppRequestMapper.class);
        restaurantAppResponseMapper = mock(IRestaurantAppResponseMapper.class);
        restaurantAppHandler = new RestaurantAppHandler(restaurantServicePort, restaurantAppRequestMapper, restaurantAppResponseMapper);
    }

    @Test
    void saveRestaurantInRestaurantApp_shouldCallService() {
        RestaurantAppRequestDto requestDto = new RestaurantAppRequestDto();
        Restaurant restaurant = new Restaurant();

        when(restaurantAppRequestMapper.toRestaurant(requestDto)).thenReturn(restaurant);

        restaurantAppHandler.saveRestaurantInRestaurantApp(requestDto);

        verify(restaurantAppRequestMapper).toRestaurant(requestDto);
        verify(restaurantServicePort).saveRestaurant(restaurant);
    }

    @Test
    void listRestaurants_shouldReturnPageResponseDto() {
        int page = 0;
        int size = 2;

        Restaurant r1 = new Restaurant(1L, "Resto 1", 123L, "Dir1", "+573001", "logo1.png", 10L);
        Restaurant r2 = new Restaurant(2L, "Resto 2", 456L, "Dir2", "+573002", "logo2.png", 20L);
        PageModel<Restaurant> pageModel = new PageModel<>(List.of(r1, r2), page, size, 2L);

        when(restaurantServicePort.getAllRestaurants(any())).thenReturn(pageModel);

        PageResponseDto<RestaurantSummaryResponseDto> response = restaurantAppHandler.listRestaurants(page, size);

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals("Resto 1", response.getContent().get(0).getNameRestaurant());
        assertEquals("logo1.png", response.getContent().get(0).getUrlLogo());
        assertEquals("Resto 2", response.getContent().get(1).getNameRestaurant());
        assertEquals("logo2.png", response.getContent().get(1).getUrlLogo());
        assertEquals(2L, response.getTotalElements());
        assertEquals(page, response.getPageNumber());
        assertEquals(size, response.getPageSize());

        verify(restaurantServicePort).getAllRestaurants(any());
    }

    @Test
    void findRestaurantById_shouldReturnRestaurantIdDto() {
        Long id = 5L;
        Restaurant restaurant = new Restaurant();
        restaurant.setIdRestaurant(id);
        RestaurantIdResponseDto responseDto = new RestaurantIdResponseDto(id);

        when(restaurantServicePort.findRestaurantById(id)).thenReturn(restaurant);
        when(restaurantAppResponseMapper.toRestaurantIdResponseDto(restaurant)).thenReturn(responseDto);

        RestaurantIdResponseDto result = restaurantAppHandler.findRestaurantById(id);

        assertEquals(id, result.getId());
        verify(restaurantServicePort).findRestaurantById(id);
        verify(restaurantAppResponseMapper).toRestaurantIdResponseDto(restaurant);
    }

    @Test
    void getRestaurantIdByUserId_shouldReturnId() {
        Long userId = 77L;
        Long restaurantId = 99L;
        Restaurant restaurant = new Restaurant();
        restaurant.setIdRestaurant(restaurantId);

        when(restaurantServicePort.findByUserId(userId)).thenReturn(restaurant);

        Long result = restaurantAppHandler.getRestaurantIdByUserId(userId);

        assertEquals(restaurantId, result);
        verify(restaurantServicePort).findByUserId(userId);
    }

    @Test
    void getRestaurantIdByRestaurantId_shouldReturnId() {
        Long restaurantId = 88L;
        Restaurant restaurant = new Restaurant();
        restaurant.setIdRestaurant(restaurantId);

        when(restaurantServicePort.findRestaurantById(restaurantId)).thenReturn(restaurant);

        Long result = restaurantAppHandler.getRestaurantIdByRestaurantId(restaurantId);

        assertEquals(restaurantId, result);
        verify(restaurantServicePort).findRestaurantById(restaurantId);
    }
}
