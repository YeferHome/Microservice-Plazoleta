package retoPragma.MicroPlazoleta.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.mapper.IDishAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPlatoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IDishServicePort;
import retoPragma.MicroPlazoleta.domain.model.Dish;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatoAppHandler implements IPlatoAppHandler {

    private final IDishServicePort platoServicePort;
    private final IDishAppRequestMapper platoAppRequestMapper;
    private final IPlatoAppResponseMapper platoAppResponseMapper;

    @Override
    public void savePlatoInPlatoApp(DishAppRequestDto dishAppRequestDto) {
        Dish dish = platoAppRequestMapper.toDish(dishAppRequestDto);
        platoServicePort.saveDish(dish);
    }

    @Override
    public PlatoUpdateResponseDto updatePlatoInPlatoApp(Long platoId, DishUpdateRequestDto dishUpdateRequestDto) {
        Long usuarioId = getUsuarioIdFromContext();
        Dish dishModificado = platoAppRequestMapper.toDishUpdate(dishUpdateRequestDto);
        Dish dishActualizado = platoServicePort.updateDish(platoId, dishModificado, usuarioId);
        return platoAppResponseMapper.toPlatoUpdateResponseDto(dishActualizado);
    }

    @Override
    public PlatoUpdateEstadoResponseDto updateEstadoPlatoInPlatoApp(Long platoId, Boolean nuevoEstado) {
        Long usuarioId = getUsuarioIdFromContext();
        Dish dishActualizado = platoServicePort.updateEstateDish(platoId, nuevoEstado, usuarioId);
        return new PlatoUpdateEstadoResponseDto(dishActualizado.getEstate());
    }

    @Override
    public List<PlatoAppResponseDto> listPlatosMenu(Long idRestaurante, String categoria, int page, int size) {
        List<Dish> dishes = platoServicePort.getDishByRestaurant(idRestaurante, categoria, page, size);
        return platoAppResponseMapper.PlatoAppResponseDtoList(dishes);
    }

    private Long getUsuarioIdFromContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
