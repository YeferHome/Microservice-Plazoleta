package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long idClient;
    private Long idRestaurant;
    private List<OrderItemRequestDto> items;
    private Long EmployeeAssigned;
    private String pin;
}
