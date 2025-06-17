package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishUpdateResponseDto {
    private String nameDish;
    private String descriptionDish;
    private Long priceDish;
}
