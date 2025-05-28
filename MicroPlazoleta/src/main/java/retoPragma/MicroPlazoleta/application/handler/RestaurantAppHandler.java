package retoPragma.MicroPlazoleta.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantSummaryResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IRestaurantAppRequestMapper;
import retoPragma.MicroPlazoleta.domain.api.IRestaurantServicePort;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantAppHandler implements IRestaurantAppHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantAppRequestMapper restaurantAppRequestMapper;

    @Override
    public void saveRestaurantInRestaurantApp(RestaurantAppRequestDto restaurantAppRequestDto) {
        Restaurant restaurant = restaurantAppRequestMapper.toRestaurant(restaurantAppRequestDto);
        restaurantServicePort.saveRestaurant(restaurant);
    }

    @Override
    public PageResponseDto<RestaurantSummaryResponseDto> listRestaurants(int page, int size) {
        PageRequestModel requestModel = new PageRequestModel(page, size);
        PageModel<Restaurant> restaurantPage = restaurantServicePort.getAllRestaurants(requestModel);

        List<RestaurantSummaryResponseDto> content = restaurantPage.getContent().stream()
                .map(r -> new RestaurantSummaryResponseDto(r.getNameRestaurant(), r.getUrlLogo()))
                .toList();

        return new PageResponseDto<>(
                content,
                restaurantPage.getPageNumber(),
                restaurantPage.getPageSize(),
                restaurantPage.getTotalElements()
        );
    }
}
