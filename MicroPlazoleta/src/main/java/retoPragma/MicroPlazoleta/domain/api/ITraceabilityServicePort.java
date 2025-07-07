package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.OrderTraceabilityRequestModel;

public interface ITraceabilityServicePort {
    void sendTraceability(OrderTraceabilityRequestModel traceability, String token);
}
