package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidoResponseDto {

    private Long idPedido;
    private EstadoPedido estado;
    private Long idCliente;
    private Long idRestaurante;
    private List<PedidoItemResponseDto> items;

}
