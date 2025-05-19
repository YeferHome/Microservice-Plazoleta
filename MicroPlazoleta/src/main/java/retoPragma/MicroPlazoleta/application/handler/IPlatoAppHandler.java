package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.*;

import java.util.List;

public interface IPlatoAppHandler {

    void savePlatoInPlatoApp(DishAppRequestDto dishAppRequestDto);
    PlatoUpdateResponseDto updatePlatoInPlatoApp(Long platoId, DishUpdateRequestDto dishUpdateRequestDto);
    PlatoUpdateEstadoResponseDto updateEstadoPlatoInPlatoApp(Long platoId, Boolean nuevoEstado);
    List<PlatoAppResponseDto> listPlatosMenu(Long idRestaurante, String categoria, int page, int size);
}