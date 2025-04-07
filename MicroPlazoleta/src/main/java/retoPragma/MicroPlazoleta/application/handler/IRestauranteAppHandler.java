package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;

public interface IRestauranteAppHandler {

    void saveRestauranteInRestauranteApp(RestauranteAppRequestDto restauranteAppRequestDto);
}