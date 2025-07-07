package retoPragma.MicroPlazoleta.infrastructure.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import retoPragma.MicroPlazoleta.domain.model.OrderTraceabilityRequestModel;
import retoPragma.MicroPlazoleta.application.dto.OrderTraceabilityRequestDto;
import retoPragma.MicroPlazoleta.domain.model.TraceabilityTimestamp;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityMapper {

    OrderTraceabilityRequestDto toDto(OrderTraceabilityRequestModel model);

    default LocalDateTime map(TraceabilityTimestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
