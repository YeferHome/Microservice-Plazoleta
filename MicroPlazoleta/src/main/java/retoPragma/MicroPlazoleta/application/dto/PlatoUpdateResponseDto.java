package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoUpdateResponseDto {
    private String nombrePlato;
    private String descripcionPlato;
    private Long precioPlato;
}
