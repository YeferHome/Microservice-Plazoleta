package retoPragma.MicroPlazoleta.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.application.dto.OrderTraceabilityRequestDto;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityRequestMapper {

    OrderTraceabilityRequestDto toRequestDto(OrderTraceabilityRequestDto traceabilityDto);

    OrderTraceabilityRequestDto toDto(OrderTraceabilityRequestDto traceabilityDto);
}
