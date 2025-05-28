package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.*;

public interface IDishAppHandler {

    void saveDishInDishApp(DishAppRequestDto dishAppRequestDto);

    DishUpdateResponseDto updateDishInDishApp(Long dishId, DishUpdateRequestDto dishUpdateRequestDto);

    DishUpdateEstateResponseDto updateEstateDishInDishApp(Long dishId, Boolean newState);

    PageResponseDto<DishAppResponseDto> listDishMenu(Long idRestaurant, String category, int page, int size);
}
