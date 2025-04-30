package retoPragma.MicroPlazoleta.application.handler;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestauranteResumenResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IRestauranteAppRequestMapper;
import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;

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
        Restaurante restaurante = restauranteAppRequestMapper.toRestaurante(restauranteAppRequestDto);
        restauranteServicePort.saveRestaurante(restaurante);
    }
    @Override
    public List<RestauranteResumenResponseDto> listRestaurantes(int page, int size) {
        List<Restaurante> restaurantes = restauranteServicePort.getAllRestaurantes(page, size);

        return restaurantes.stream()
                .map(r -> new RestauranteResumenResponseDto(r.getNombreRestaurante(), r.getUrlLogo()))
                .collect(Collectors.toList());
    }

}
