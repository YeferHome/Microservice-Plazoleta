package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RestauranteAppResponseDto {

    private String nombreRestaurante;
    private Long nit;
    private String direccion;
    private String telefonoRestaurante;
    private String urlLogo;
    private Long idUsuario;
}

