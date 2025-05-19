package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishUpdateRequestDto {
    private String descripcionPlato;
    private Long precioPlato;
}
