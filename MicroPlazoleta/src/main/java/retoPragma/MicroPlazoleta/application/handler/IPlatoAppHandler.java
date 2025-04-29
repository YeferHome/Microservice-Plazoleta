package retoPragma.MicroPlazoleta.application.handler;

import retoPragma.MicroPlazoleta.application.dto.PlatoAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateEstadoResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateResponseDto;

public interface IPlatoAppHandler {

    void savePlatoInPlatoApp(PlatoAppRequestDto platoAppRequestDto);
    PlatoUpdateResponseDto updatePlatoInPlatoApp(Long platoId, PlatoUpdateRequestDto platoUpdateRequestDto, Long usuarioId);
    PlatoUpdateEstadoResponseDto updateEstadoPlatoInPlatoApp(Long platoId, Boolean nuevoEstado, Long usuarioId);

}