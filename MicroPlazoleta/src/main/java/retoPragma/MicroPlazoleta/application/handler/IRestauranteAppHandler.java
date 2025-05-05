package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestauranteResumenResponseDto;

import java.util.List;

public interface IRestauranteAppHandler {

    void saveRestauranteInRestauranteApp(RestauranteAppRequestDto restauranteAppRequestDto);
    List<RestauranteResumenResponseDto> listRestaurantes(int page, int size);
} 