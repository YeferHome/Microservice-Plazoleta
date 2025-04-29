package retoPragma.MicroPlazoleta.application.handler;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.PlatoAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateEstadoResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateResponseDto;
import retoPragma.MicroPlazoleta.application.mapper.IPlatoAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPlatoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.model.Plato;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatoAppHandler implements IPlatoAppHandler {

    private final IPlatoServicePort platoServicePort;
    private final IPlatoAppRequestMapper platoAppRequestMapper;
    private final IPlatoAppResponseMapper platoAppResponseMapper;


    @Override
    public void savePlatoInPlatoApp(PlatoAppRequestDto platoAppRequestDto) {
        Plato plato = platoAppRequestMapper.toPlato(platoAppRequestDto);
        platoServicePort.savePlato(plato);
    }


    @Override
    public PlatoUpdateResponseDto updatePlatoInPlatoApp(Long platoId, PlatoUpdateRequestDto platoUpdateRequestDto, Long usuarioId) {
        Plato platoModificado = platoAppRequestMapper.toPlatoUpdate(platoUpdateRequestDto);
        Plato platoActualizado = platoServicePort.updatePlato(platoId, platoModificado, usuarioId);
        return platoAppResponseMapper.toPlatoUpdateResponseDto(platoActualizado);
    }

    @Override
    public PlatoUpdateEstadoResponseDto updateEstadoPlatoInPlatoApp(Long platoId, Boolean nuevoEstado, Long usuarioId) {
        Plato platoActualizado = platoServicePort.updateEstadoPlato(platoId, nuevoEstado, usuarioId);


        return new PlatoUpdateEstadoResponseDto(
                platoActualizado.getEstado());
    }

}
