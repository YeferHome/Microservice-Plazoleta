package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {
    private Long idDish;
    private Long idRestaurant;
    private int amount;
}