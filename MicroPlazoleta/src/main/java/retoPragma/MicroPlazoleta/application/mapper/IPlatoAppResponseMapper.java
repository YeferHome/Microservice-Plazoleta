package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.PlatoAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoAppResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateEstadoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PlatoUpdateResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Plato;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlatoAppResponseMapper {

    PlatoAppResponseDto toPlatoAppResponseDto(Plato plato);
    PlatoUpdateResponseDto toPlatoUpdateResponseDto(Plato plato);
    PlatoUpdateEstadoRequestDto toPlatoUpdateEstadoRequestDto(Plato plato);

}
