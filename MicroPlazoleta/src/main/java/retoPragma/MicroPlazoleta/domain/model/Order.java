package retoPragma.MicroPlazoleta.domain.model;

import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

public class Order {
    private Long idOrder;
    private Long idClient;
    private Long idRestaurant;
    private EstateOrder estate;
    private List<OrderItem> items;
    private Long EmployeeAssigned;
    private String pin;

    public Order() {
    }

    public Order(Long idOrder, Long idClient, Long idRestaurant, EstateOrder estate, List<OrderItem> items, Long EmployeeAssigned, String pin) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idRestaurant = idRestaurant;
        this.estate = estate;
        this.items = items;
        this.EmployeeAssigned = EmployeeAssigned;
        this.pin = pin;
    }

    public Long getEmployeeAssigned() {
        return EmployeeAssigned;
    }
    public void setEmployeeAssigned(Long employeeAssigned) {
        EmployeeAssigned = employeeAssigned;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public EstateOrder getEstate() {
        return estate;
    }

    public void setEstate(EstateOrder estate) {
        this.estate = estate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
