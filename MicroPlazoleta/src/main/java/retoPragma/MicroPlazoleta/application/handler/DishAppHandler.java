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
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishAppHandler implements IDishAppHandler {

    private final IDishServicePort platoServicePort;
    private final IDishAppRequestMapper dishAppRequestMapper;
    private final IPlatoAppResponseMapper dishAppResponseMapper;

    @Override
    public void saveDishInDishApp(DishAppRequestDto dishAppRequestDto) {
        Dish dish = dishAppRequestMapper.toDish(dishAppRequestDto);
        platoServicePort.saveDish(dish);
    }

    @Override
    public DishUpdateResponseDto updateDishInDishApp(Long dishId, DishUpdateRequestDto dishUpdateRequestDto) {
        Long usuarioId = getUsuarioIdFromContext();
        Dish dishModificado = dishAppRequestMapper.toDishUpdate(dishUpdateRequestDto);
        Dish dishActualizado = platoServicePort.updateDish(dishId, dishModificado, usuarioId);
        return dishAppResponseMapper.toDishUpdateResponseDto(dishActualizado);
    }

    @Override
    public DishUpdateEstateResponseDto updateEstateDishInDishApp(Long dishId, Boolean newState) {
        Long usuarioId = getUsuarioIdFromContext();
        Dish dishActualizado = platoServicePort.updateEstateDish(dishId, newState, usuarioId);
        return new DishUpdateEstateResponseDto(dishActualizado.getEstate());
    }

    @Override
    public PageResponseDto<DishAppResponseDto> listDishMenu(Long idRestaurant, String category, int page, int size) {
        PageRequestModel pageRequestModel = new PageRequestModel(page, size);
        PageModel<Dish> pageModel = platoServicePort.getDishesByRestaurantAndOptionalCategory(idRestaurant, category, pageRequestModel);

        List<DishAppResponseDto> content = new ArrayList<>();
        for (Dish dish : pageModel.getContent()) {
            content.add(dishAppResponseMapper.toDishAppResponseDto(dish));
        }

        return new PageResponseDto<>(
                content,
                pageModel.getPageNumber(),
                pageModel.getPageSize(),
                pageModel.getTotalElements()
        );
    }

    private Long getUsuarioIdFromContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
