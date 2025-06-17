package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantAppRequestDto {

    private String nameRestaurant;
    private Long nit;
    private String address;
    private String phoneRestaurant;
    private String urlLogo;
    private Long idUser;
}