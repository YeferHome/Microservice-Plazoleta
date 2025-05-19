package retoPragma.MicroPlazoleta.application.handler;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestauranteResumenResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IRestauranteAppRequestMapper;
import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestauranteAppHandler implements IRestauranteAppHandler {

    private final IRestauranteServicePort restauranteServicePort;
    private final IRestauranteAppRequestMapper restauranteAppRequestMapper;


    @Override
    public void saveRestauranteInRestauranteApp(RestauranteAppRequestDto restauranteAppRequestDto) {
        Restaurant restaurant = restauranteAppRequestMapper.toRestaurante(restauranteAppRequestDto);
        restauranteServicePort.saveRestaurante(restaurant);
    }
    @Override
    public List<RestauranteResumenResponseDto> listRestaurantes(int page, int size) {
        List<Restaurant> restaurants = restauranteServicePort.getAllRestaurantes(page, size);

        return restaurants.stream()
                .map(r -> new RestauranteResumenResponseDto(r.getNombreRestaurante(), r.getUrlLogo()))
                .collect(Collectors.toList());
    }

}
