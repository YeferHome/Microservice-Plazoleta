package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppResponseDto;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestauranteAppResponseMapper {
    RestauranteAppResponseDto toRestauranteAppResponseDto(Restaurante restaurante);
}
