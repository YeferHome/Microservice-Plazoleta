package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoItemRequestDto {
    private Long idPlato;
    private Long idRestaurante;
    private int cantidad;
}