package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long idDish;
    private int amount ;
}