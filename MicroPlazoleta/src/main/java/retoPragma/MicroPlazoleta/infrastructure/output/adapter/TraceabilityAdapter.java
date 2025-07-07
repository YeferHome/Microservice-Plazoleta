package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import retoPragma.MicroPlazoleta.domain.api.ITraceabilityServicePort;
import retoPragma.MicroPlazoleta.domain.model.OrderTraceabilityRequestModel;
import retoPragma.MicroPlazoleta.infrastructure.input.client.TraceabilityFeignClient;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.ITraceabilityMapper;

@RequiredArgsConstructor
public class TraceabilityAdapter implements ITraceabilityServicePort {

    private final TraceabilityFeignClient traceabilityFeignClient;
    private final ITraceabilityMapper traceabilityRequestMapper;

    @Override
    public void sendTraceability(OrderTraceabilityRequestModel requestModel, String token) {
        traceabilityFeignClient.registerTraceability(
                traceabilityRequestMapper.toDto(requestModel),
                token
        );
    }
}
