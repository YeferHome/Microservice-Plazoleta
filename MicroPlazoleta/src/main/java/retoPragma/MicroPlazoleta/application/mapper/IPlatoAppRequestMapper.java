package retoPragma.MicroPlazoleta.application.mapper;

import retoPragma.MicroPlazoleta.application.dto.PlatoAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateEstadoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateRequestDto;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoAppRequestMapper {

    Plato toPlato(PlatoAppRequestDto platoAppRequestDto);
    Plato toPlatoUpdate(PlatoUpdateRequestDto platoUpdateRequestDto);
    String toEstadoString(PlatoUpdateEstadoRequestDto platoUpdateEstadoRequestDto);
}
