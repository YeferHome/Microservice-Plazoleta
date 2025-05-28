package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishUpdateResponseDto {
    private String nombrePlato;
    private String descripcionPlato;
    private Long precioPlato;
}
