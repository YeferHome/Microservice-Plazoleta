package retoPragma.MicroPlazoleta.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.mapper.IPlatoAppRequestMapper;
import retoPragma.MicroPlazoleta.application.mapper.IPlatoAppResponseMapper;
import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.model.Plato;

import java.util.List;

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
    public PlatoUpdateResponseDto updatePlatoInPlatoApp(Long platoId, PlatoUpdateRequestDto platoUpdateRequestDto) {
        Long usuarioId = getUsuarioIdFromContext();
        Plato platoModificado = platoAppRequestMapper.toPlatoUpdate(platoUpdateRequestDto);
        Plato platoActualizado = platoServicePort.updatePlato(platoId, platoModificado, usuarioId);
        return platoAppResponseMapper.toPlatoUpdateResponseDto(platoActualizado);
    }

    @Override
    public PlatoUpdateEstadoResponseDto updateEstadoPlatoInPlatoApp(Long platoId, Boolean nuevoEstado) {
        Long usuarioId = getUsuarioIdFromContext();
        Plato platoActualizado = platoServicePort.updateEstadoPlato(platoId, nuevoEstado, usuarioId);
        return new PlatoUpdateEstadoResponseDto(platoActualizado.getEstado());
    }

    @Override
    public List<PlatoAppResponseDto> listPlatosMenu(Long idRestaurante, String categoria, int page, int size) {
        List<Plato> platos = platoServicePort.getPlatosByRestaurante(idRestaurante, categoria, page, size);
        return platoAppResponseMapper.PlatoAppResponseDtoList(platos);
    }

    private Long getUsuarioIdFromContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
