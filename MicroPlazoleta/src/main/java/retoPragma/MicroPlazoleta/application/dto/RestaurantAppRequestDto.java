package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantAppRequestDto {

    private String nombreRestaurante;
    private Long nit;
    private String direccion;
    private String telefonoRestaurante;
    private String urlLogo;
    private Long idUsuario;
}