package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantIdResponseDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantSummaryResponseDto;

public interface IRestaurantAppHandler {

    void saveRestaurantInRestaurantApp(RestaurantAppRequestDto restaurantAppRequestDto);
    PageResponseDto<RestaurantSummaryResponseDto> listRestaurants(int page, int size);
    RestaurantIdResponseDto findRestaurantById(Long id);
    Long getRestaurantIdByUserId(Long userId);
    Long getRestaurantIdByRestaurantId (Long restaurantId);


}