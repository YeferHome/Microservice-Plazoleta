package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.application.dto.OrderTraceabilityRequestDto;

public interface IMessagingServicePort {
    void sendTraceability(OrderTraceabilityRequestDto traceabilityRequestDto, String token);
}
