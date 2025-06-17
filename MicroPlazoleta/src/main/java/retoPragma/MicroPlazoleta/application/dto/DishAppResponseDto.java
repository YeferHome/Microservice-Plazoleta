package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DishAppResponseDto {
    private String nameDish;
    private String descriptionDish;
    private Long priceDish;
    private String urlDish;
    private String categoryDish;
    private Long idRestaurant;
    private Long idUser;
}
