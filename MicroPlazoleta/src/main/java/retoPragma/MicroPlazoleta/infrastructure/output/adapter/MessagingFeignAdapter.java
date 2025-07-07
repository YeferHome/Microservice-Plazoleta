package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import retoPragma.MicroPlazoleta.application.dto.OrderTraceabilityRequestDto;
import retoPragma.MicroPlazoleta.domain.spi.IMessagingServicePort;
import retoPragma.MicroPlazoleta.infrastructure.input.client.TraceabilityFeignClient;

@Component
@RequiredArgsConstructor
public class MessagingFeignAdapter implements IMessagingServicePort {

    private final TraceabilityFeignClient traceabilityFeignClient;

    @Override
    public void sendTraceability(OrderTraceabilityRequestDto traceabilityRequestDto, String token) {
        traceabilityFeignClient.registerTraceability(traceabilityRequestDto, "Bearer " + token);
    }
}
