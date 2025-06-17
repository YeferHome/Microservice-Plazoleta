package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RestaurantAppResponseDto {

    private String nameRestaurant;
    private Long nit;
    private String address;
    private String phoneRestaurant;
    private String urlLogo;
    private Long idUser;
}

