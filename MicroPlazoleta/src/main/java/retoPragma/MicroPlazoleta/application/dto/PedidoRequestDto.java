package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDto {
    private Long idCliente;
    private Long idRestaurante;
    private List<PedidoItemRequestDto> items;
}
