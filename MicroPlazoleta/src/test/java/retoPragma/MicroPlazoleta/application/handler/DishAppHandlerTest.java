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
import retoPragma.MicroPlazoleta.application.mapper.IDishAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IDishServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.PageModel;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishAppHandlerTest {

    private IDishServicePort platoServicePort;
    private IDishAppRequestMapper platoAppRequestMapper;
    private IDishAppResponseMapper platoAppResponseMapper;
    private DishAppHandler platoAppHandler;

    @BeforeEach
    void setUp() {
        platoServicePort = mock(IDishServicePort.class);
        platoAppRequestMapper = mock(IDishAppRequestMapper.class);
        platoAppResponseMapper = mock(IDishAppResponseMapper.class);
        platoAppHandler = new DishAppHandler(platoServicePort, platoAppRequestMapper, platoAppResponseMapper);
    }

    @Test
    void savePlatoInPlatoApp() {
        DishAppRequestDto requestDto = new DishAppRequestDto();
        Dish dish = new Dish();

        when(platoAppRequestMapper.toDish(requestDto)).thenReturn(dish);

        platoAppHandler.saveDishInDishApp(requestDto);

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
        DishUpdateResponseDto expectedResponse = new DishUpdateResponseDto();

        when(platoAppRequestMapper.toDishUpdate(updateRequest)).thenReturn(dishUpdate);
        when(platoServicePort.updateDish(platoId, dishUpdate, usuarioId)).thenReturn(dishActualizado);
        when(platoAppResponseMapper.toDishUpdateResponseDto(dishActualizado)).thenReturn(expectedResponse);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockSecurityContext(usuarioId)) {
            DishUpdateResponseDto result = platoAppHandler.updateDishInDishApp(platoId, updateRequest);

            assertEquals(expectedResponse, result);
            verify(platoServicePort).updateDish(platoId, dishUpdate, usuarioId);
            verify(platoAppResponseMapper).toDishUpdateResponseDto(dishActualizado);
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
            DishUpdateEstateResponseDto result = platoAppHandler.updateEstateDishInDishApp(platoId, nuevoEstado);

            assertNotNull(result);
            assertEquals(nuevoEstado, result.isEstate());
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
        PageModel<Dish> pageModel = new PageModel<>(dishes, page, size, 2L);

        DishAppResponseDto dto1 = new DishAppResponseDto(
                "Plato1", "Descripcion1", 10000L, "url1", "Entradas", 1L, 123L);
        DishAppResponseDto dto2 = new DishAppResponseDto(
                "Plato2", "Descripcion2", 15000L, "url2", "Entradas", 1L, 123L);
        List<DishAppResponseDto> expectedList = Arrays.asList(dto1, dto2);

        when(platoServicePort.getDishesByRestaurantAndOptionalCategory(eq(idRestaurante), eq(categoria), any()))
                .thenReturn(pageModel);
        when(platoAppResponseMapper.toDishAppResponseDto(dish1)).thenReturn(dto1);
        when(platoAppResponseMapper.toDishAppResponseDto(dish2)).thenReturn(dto2);

        PageResponseDto<DishAppResponseDto> result = platoAppHandler.listDishMenu(idRestaurante, categoria, page, size);

        assertEquals(2, result.getContent().size());
        assertEquals(dto1, result.getContent().get(0));
        assertEquals(dto2, result.getContent().get(1));
        assertEquals(2L, result.getTotalElements());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());

        verify(platoServicePort).getDishesByRestaurantAndOptionalCategory(eq(idRestaurante), eq(categoria), any());
        verify(platoAppResponseMapper).toDishAppResponseDto(dish1);
        verify(platoAppResponseMapper).toDishAppResponseDto(dish2);
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
