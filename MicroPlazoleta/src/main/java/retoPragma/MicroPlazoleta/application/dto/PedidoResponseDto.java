package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidoResponseDto {

    private Long idPedido;
    private EstateOrder estado;
    private Long idCliente;
    private Long idRestaurante;
    private List<PedidoItemResponseDto> items;

}
