package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishAppRequestDto {
    private String nameDish;
    private String descriptionDish;
    private Long priceDish;
    private String urlDish;
    private String categoryDish;
    private Long idRestaurant;
    private Long idUser;
}