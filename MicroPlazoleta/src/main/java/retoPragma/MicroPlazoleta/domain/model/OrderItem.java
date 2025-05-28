package retoPragma.MicroPlazoleta.domain.model;


public class OrderItem {
    private Long idOrderItem;
    private Long idDish;
    private Long idRestaurant;
    private int amount;

    public OrderItem() {
    }

    public OrderItem(Long idOrderItem, Long idDish, Long idRestaurant, int amount) {
        this.idOrderItem = idOrderItem;
        this.idDish = idDish;
        this.idRestaurant = idRestaurant;
        this.amount = amount;
    }

    public Long getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(Long idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
