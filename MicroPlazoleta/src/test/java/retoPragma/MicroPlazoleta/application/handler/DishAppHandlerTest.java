package retoPragma.MicroPlazoleta.application.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.mapper.IDishAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPlatoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IDishServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishAppHandlerTest {

    private IDishServicePort platoServicePort;
    private IDishAppRequestMapper platoAppRequestMapper;
    private IPlatoAppResponseMapper platoAppResponseMapper;
    private PlatoAppHandler platoAppHandler;

    @BeforeEach
    void setUp() {
        platoServicePort = mock(IDishServicePort.class);
        platoAppRequestMapper = mock(IDishAppRequestMapper.class);
        platoAppResponseMapper = mock(IPlatoAppResponseMapper.class);
        platoAppHandler = new PlatoAppHandler(platoServicePort, platoAppRequestMapper, platoAppResponseMapper);
    }

    @Test
    void savePlatoInPlatoApp() {
        DishAppRequestDto requestDto = new DishAppRequestDto();
        Dish dish = new Dish();

        when(platoAppRequestMapper.toDish(requestDto)).thenReturn(dish);

        platoAppHandler.savePlatoInPlatoApp(requestDto);

        verify(platoAppRequestMapper).toDish(requestDto);
        verify(platoServicePort).saveDish(dish);
    }

    @Test
    void updatePlatoInPlatoApp() {
        Long platoId = 1L;
        Long usuarioId = 123L;
        DishUpdateRequestDto updateRequest = new DishUpdateRequestDto();
        Dish dishUpdate = new Dish();
        Dish dishActualizado = new Dish();
        PlatoUpdateResponseDto expectedResponse = new PlatoUpdateResponseDto();

        when(platoAppRequestMapper.toDishUpdate(updateRequest)).thenReturn(dishUpdate);
        when(platoServicePort.updateDish(platoId, dishUpdate, usuarioId)).thenReturn(dishActualizado);
        when(platoAppResponseMapper.toPlatoUpdateResponseDto(dishActualizado)).thenReturn(expectedResponse);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockSecurityContext(usuarioId)) {
            PlatoUpdateResponseDto result = platoAppHandler.updatePlatoInPlatoApp(platoId, updateRequest);

            assertEquals(expectedResponse, result);
            verify(platoServicePort).updateDish(platoId, dishUpdate, usuarioId);
            verify(platoAppResponseMapper).toPlatoUpdateResponseDto(dishActualizado);
        }
    }

    @Test
    void updateEstadoPlatoInPlatoApp() {
        Long platoId = 1L;
        Boolean nuevoEstado = false;
        Long usuarioId = 123L;
        Dish dishActualizado = new Dish();
        dishActualizado.setEstate(nuevoEstado);

        when(platoServicePort.updateEstateDish(platoId, nuevoEstado, usuarioId)).thenReturn(dishActualizado);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockSecurityContext(usuarioId)) {
            PlatoUpdateEstadoResponseDto result = platoAppHandler.updateEstadoPlatoInPlatoApp(platoId, nuevoEstado);

            assertNotNull(result);
            assertEquals(nuevoEstado, result.isEstado());
            verify(platoServicePort).updateEstateDish(platoId, nuevoEstado, usuarioId);
        }
    }

    @Test
    void listPlatosMenu() {
        Long idRestaurante = 1L;
        String categoria = "Entradas";
        int page = 0;
        int size = 5;

        Dish dish1 = new Dish();
        Dish dish2 = new Dish();
        List<Dish> dishes = Arrays.asList(dish1, dish2);

        PlatoAppResponseDto dto1 = new PlatoAppResponseDto(
                "Plato1", "Descripcion1", 10000L, "url1", "Entradas", 1L, 123L);
        PlatoAppResponseDto dto2 = new PlatoAppResponseDto(
                "Plato2", "Descripcion2", 15000L, "url2", "Entradas", 1L, 123L);
        List<PlatoAppResponseDto> expectedList = Arrays.asList(dto1, dto2);

        when(platoServicePort.getDishByRestaurant(idRestaurante, categoria, page, size)).thenReturn(dishes);
        when(platoAppResponseMapper.PlatoAppResponseDtoList(dishes)).thenReturn(expectedList);

        List<PlatoAppResponseDto> result = platoAppHandler.listPlatosMenu(idRestaurante, categoria, page, size);

        assertEquals(2, result.size());
        verify(platoServicePort).getDishByRestaurant(idRestaurante, categoria, page, size);
        verify(platoAppResponseMapper).PlatoAppResponseDtoList(dishes);
    }

    private MockedStatic<SecurityContextHolder> mockSecurityContext(Long usuarioId) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(usuarioId.toString());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        MockedStatic<SecurityContextHolder> contextHolder = Mockito.mockStatic(SecurityContextHolder.class);
        contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        return contextHolder;
    }
}
