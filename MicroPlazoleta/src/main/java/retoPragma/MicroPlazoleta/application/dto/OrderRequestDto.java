package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Long idClient;
    private Long idRestaurant;
    private List<OrderItemRequestDto> items;
    private Long EmployeeAssigned;
    private String pin;
}
