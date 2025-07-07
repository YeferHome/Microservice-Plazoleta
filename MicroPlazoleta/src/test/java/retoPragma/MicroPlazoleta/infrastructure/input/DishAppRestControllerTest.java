package retoPragma.MicroPlazoleta.infrastructure.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import retoPragma.MicroPlazoleta.application.dto.*;

import retoPragma.MicroPlazoleta.application.handler.IDishAppHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishAppRestControllerTest {

    @Mock
    private IDishAppHandler dishAppHandler;

    @InjectMocks
    private DishAppRestController dishAppRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDishInDishApp_shouldReturnCreated() {
        DishAppRequestDto requestDto = new DishAppRequestDto();

        doNothing().when(dishAppHandler).saveDishInDishApp(requestDto);

        ResponseEntity<Void> response = dishAppRestController.saveDishInDishApp(requestDto);

        verify(dishAppHandler).saveDishInDishApp(requestDto);
        assertEquals(201, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updateDishInDishApp_shouldReturnUpdatedDish() {
        Long idDish = 1L;
        DishUpdateRequestDto requestDto = new DishUpdateRequestDto();
        DishUpdateResponseDto expectedResponse = new DishUpdateResponseDto();

        when(dishAppHandler.updateDishInDishApp(idDish, requestDto)).thenReturn(expectedResponse);

        ResponseEntity<DishUpdateResponseDto> response = dishAppRestController.updateDishInDishApp(idDish, requestDto);

        verify(dishAppHandler).updateDishInDishApp(idDish, requestDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void updateEstateDish_shouldReturnUpdatedEstate() {
        Long idDish = 1L;
        DishUpdateEstateRequestDto requestDto = new DishUpdateEstateRequestDto(true);
        DishUpdateEstateResponseDto expectedResponse = new DishUpdateEstateResponseDto(true);

        when(dishAppHandler.updateEstateDishInDishApp(idDish, true)).thenReturn(expectedResponse);

        ResponseEntity<DishUpdateEstateResponseDto> response = dishAppRestController.updateEstateDish(idDish, requestDto);

        verify(dishAppHandler).updateEstateDishInDishApp(idDish, true);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getMenuRestaurant_shouldReturnMenuPage() {
        Long restaurantId = 1L;
        String category = "main";
        int page = 0;
        int size = 10;

        List<DishAppResponseDto> dishes = List.of(
                new DishAppResponseDto("Dish 1", "Desc 1", 100L, "url1", "main", restaurantId, 1L),
                new DishAppResponseDto("Dish 2", "Desc 2", 150L, "url2", "main", restaurantId, 2L)
        );

        PageResponseDto<DishAppResponseDto> pageResponse =
                new PageResponseDto<>(dishes, page, size, dishes.size());

        when(dishAppHandler.listDishMenu(restaurantId, category, page, size)).thenReturn(pageResponse);

        ResponseEntity<PageResponseDto<DishAppResponseDto>> response =
                dishAppRestController.getMenuRestaurant(restaurantId, category, page, size);

        verify(dishAppHandler).listDishMenu(restaurantId, category, page, size);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getContent().size());
        assertEquals("Dish 1", response.getBody().getContent().get(0).getNameDish());
    }
}
