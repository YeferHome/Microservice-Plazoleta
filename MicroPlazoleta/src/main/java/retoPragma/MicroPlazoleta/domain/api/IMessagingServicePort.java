package retoPragma.MicroPlazoleta.domain.api;

public interface IMessagingServicePort {
    void sendNotification(String phone, String message, String token);
}
