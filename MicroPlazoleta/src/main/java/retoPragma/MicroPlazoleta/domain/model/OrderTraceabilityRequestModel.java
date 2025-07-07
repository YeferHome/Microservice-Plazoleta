package retoPragma.MicroPlazoleta.domain.model;

public class OrderTraceabilityRequestModel {

    private Long orderId;
    private Long clientId;
    private String previousStatus;
    private String newStatus;
    private TraceabilityTimestamp timestamp;

    public OrderTraceabilityRequestModel(Long orderId, Long clientId, String previousStatus, String newStatus, TraceabilityTimestamp timestamp) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.timestamp = timestamp;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public TraceabilityTimestamp getTimestamp() {
        return timestamp;
    }
}
