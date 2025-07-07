package retoPragma.MicroPlazoleta.infrastructure.input.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import retoPragma.MicroPlazoleta.application.dto.OrderTraceabilityRequestDto;

@FeignClient(name = "traceabilityClient", url = "http://localhost:8084/traceability")
public interface TraceabilityFeignClient {

    @PostMapping
    void registerTraceability(@RequestBody OrderTraceabilityRequestDto request,
                              @RequestHeader("Authorization") String token);
}
