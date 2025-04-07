package retoPragma.MicroPlazoleta.application.handler;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;

@Service
@RequiredArgsConstructor
@Transactional
public class RestauranteAppHandler implements IRestauranteAppHandler {

    private final IRestauranteServicePort restauranteServicePort;


    @Override
    public void saveRestauranteInRestauranteApp(RestauranteAppRequestDto restauranteAppRequestDto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombreRestaurante(restauranteAppRequestDto.getNombreRestaurante());
        restaurante.setNit(restauranteAppRequestDto.getNit());
        restaurante.setDireccion(restauranteAppRequestDto.getDireccion());
        restaurante.setTelefonoRestaurante(restauranteAppRequestDto.getTelefonoRestaurante());
        restaurante.setUrlLogo(restauranteAppRequestDto.getUrlLogo());
        restaurante.setIdUsuario(restauranteAppRequestDto.getIdUsuario());

        restauranteServicePort.saveRestaurante(restaurante);
    }
}