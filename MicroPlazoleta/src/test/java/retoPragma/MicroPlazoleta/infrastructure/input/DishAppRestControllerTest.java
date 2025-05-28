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
    private IDishAppHandler platoAppHandler;

    @InjectMocks
    private DishAppRestController dishAppRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePlatoInPlatoApp() {
        DishAppRequestDto requestDto = mock(DishAppRequestDto.class);

        doNothing().when(platoAppHandler).saveDishInDishApp(requestDto);

        ResponseEntity<Void> response = dishAppRestController.saveDishInDishApp(requestDto);

        verify(platoAppHandler, times(1)).saveDishInDishApp(requestDto);
        assertEquals(201, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updatePlatoInPlatoApp() {
        Long idPlato = 1L;
        DishUpdateRequestDto requestDto = mock(DishUpdateRequestDto.class);
        DishUpdateResponseDto responseDto = mock(DishUpdateResponseDto.class);

        when(platoAppHandler.updateDishInDishApp(idPlato, requestDto)).thenReturn(responseDto);

        ResponseEntity<DishUpdateResponseDto> response = dishAppRestController.updateDishInDishApp(idPlato, requestDto);

        verify(platoAppHandler, times(1)).updateDishInDishApp(idPlato, requestDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void actualizarEstadoPlato() {
        Long idPlato = 1L;
        DishUpdateEstateRequestDto requestDto = mock(DishUpdateEstateRequestDto.class);
        when(requestDto.isEstado()).thenReturn(true);

        DishUpdateEstateResponseDto responseDto = new DishUpdateEstateResponseDto(true);

        when(platoAppHandler.updateEstateDishInDishApp(idPlato, true)).thenReturn(responseDto);

        ResponseEntity<DishUpdateEstateResponseDto> response = dishAppRestController.updateEstateDish(idPlato, requestDto);

        verify(platoAppHandler, times(1)).updateEstateDishInDishApp(idPlato, true);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEstado());
    }

    @Test
    void getMenuRestaurante() {
        Long idRestaurante = 1L;
        String categoria = "categoriaEjemplo";
        int page = 0;
        int size = 10;

        List<DishAppResponseDto> responseList = List.of(
                new DishAppResponseDto("Plato1", "Desc1", 100L, "url1", "cat1", idRestaurante, 1L),
                new DishAppResponseDto("Plato2", "Desc2", 200L, "url2", "cat2", idRestaurante, 2L)
        );

        PageResponseDto<DishAppResponseDto> pageResponse = new PageResponseDto<>(responseList, page, size, responseList.size());

        when(platoAppHandler.listDishMenu(idRestaurante, categoria, page, size)).thenReturn(pageResponse);

        ResponseEntity<PageResponseDto<DishAppResponseDto>> response = dishAppRestController.getMenuRestaurant(idRestaurante, categoria, page, size);

        verify(platoAppHandler, times(1)).listDishMenu(idRestaurante, categoria, page, size);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getContent().size());
        assertEquals("Plato1", response.getBody().getContent().get(0).getNombrePlato());
    }
}
