package retoPragma.MicroPlazoleta.infrastructure.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.dto.DishAppRequestDto;
import retoPragma.MicroPlazoleta.application.handler.IPlatoAppHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishAppRestControllerTest {

    @Mock
    private IPlatoAppHandler platoAppHandler;

    @InjectMocks
    private PlatoAppRestController platoAppRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePlatoInPlatoApp() {
        DishAppRequestDto requestDto = mock(DishAppRequestDto.class);

        doNothing().when(platoAppHandler).savePlatoInPlatoApp(requestDto);

        ResponseEntity<Void> response = platoAppRestController.savePlatoInPlatoApp(requestDto);

        verify(platoAppHandler, times(1)).savePlatoInPlatoApp(requestDto);
        assertEquals(201, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updatePlatoInPlatoApp() {
        Long idPlato = 1L;
        DishUpdateRequestDto requestDto = mock(DishUpdateRequestDto.class);
        PlatoUpdateResponseDto responseDto = mock(PlatoUpdateResponseDto.class);

        when(platoAppHandler.updatePlatoInPlatoApp(idPlato, requestDto)).thenReturn(responseDto);

        ResponseEntity<PlatoUpdateResponseDto> response = platoAppRestController.updatePlatoInPlatoApp(idPlato, requestDto);

        verify(platoAppHandler, times(1)).updatePlatoInPlatoApp(idPlato, requestDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void actualizarEstadoPlato() {
        Long idPlato = 1L;
        DishUpdateEstateRequestDto requestDto = mock(DishUpdateEstateRequestDto.class);
        when(requestDto.isEstado()).thenReturn(true);

        PlatoUpdateEstadoResponseDto responseDto = new PlatoUpdateEstadoResponseDto(true);

        when(platoAppHandler.updateEstadoPlatoInPlatoApp(idPlato, true)).thenReturn(responseDto);

        ResponseEntity<PlatoUpdateEstadoResponseDto> response = platoAppRestController.actualizarEstadoPlato(idPlato, requestDto);

        verify(platoAppHandler, times(1)).updateEstadoPlatoInPlatoApp(idPlato, true);
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

        List<PlatoAppResponseDto> responseList = List.of(
                new PlatoAppResponseDto("Plato1", "Desc1", 100L, "url1", "cat1", idRestaurante, 1L),
                new PlatoAppResponseDto("Plato2", "Desc2", 200L, "url2", "cat2", idRestaurante, 2L)
        );

        when(platoAppHandler.listPlatosMenu(idRestaurante, categoria, page, size)).thenReturn(responseList);

        ResponseEntity<List<PlatoAppResponseDto>> response = platoAppRestController.getMenuRestaurante(idRestaurante, categoria, page, size);

        verify(platoAppHandler, times(1)).listPlatosMenu(idRestaurante, categoria, page, size);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseList, response.getBody());
    }
}
