package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.application.dto.NotificationsRequestDto;
import retoPragma.MicroPlazoleta.domain.api.IMessagingServicePort;
import retoPragma.MicroPlazoleta.infrastructure.input.client.IMessagingFeignClient;

@Service
@AllArgsConstructor
public class MessagingAdapter implements IMessagingServicePort {

    private final IMessagingFeignClient IMessagingFeignClient;

    @Override
    public void sendNotification(String phone, String message, String token) {
        NotificationsRequestDto request = new NotificationsRequestDto(phone, message);
        IMessagingFeignClient.sendNotification(request, token);
    }

}
