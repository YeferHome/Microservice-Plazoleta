package retoPragma.MicroPlazoleta.infrastructure.input.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import retoPragma.MicroPlazoleta.application.dto.NotificationsRequestDto;
import retoPragma.MicroPlazoleta.infrastructure.configuration.feing.FeignClientConfig;

@FeignClient(name = "ms-mensajeria", url = "http://localhost:8082/notifications", configuration = FeignClientConfig.class)
public interface IMessagingFeignClient {

    @PostMapping(value = "/order-ready")
    void sendNotification(@RequestBody NotificationsRequestDto notificationsRequestDto,
                          @RequestHeader("Authorization") String token);

}
