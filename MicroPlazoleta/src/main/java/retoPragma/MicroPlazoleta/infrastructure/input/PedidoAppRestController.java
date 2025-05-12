package retoPragma.MicroPlazoleta.infrastructure.input;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IPedidoAppHandler;
import retoPragma.MicroPlazoleta.domain.model.Pedido;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

@RestController
@RequestMapping("/pedidoApp")
@RequiredArgsConstructor
public class PedidoAppRestController {

    private final IPedidoAppHandler pedidoAppHandler;

    @PostMapping("/savePedido")
    public ResponseEntity<PedidoResponseDto> savePedido(@RequestBody PedidoRequestDto pedidoRequestDto) {
        PedidoResponseDto pedidoResponseDto = pedidoAppHandler.savePedido(pedidoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDto);
    }
    @GetMapping("/estado")
    public ResponseEntity<Page<Pedido>> getPedidosPorEstado(
            @RequestParam Long restauranteId,
            @RequestParam EstadoPedido estado,
            @RequestParam int page,
            @RequestParam int size) {

        Page<Pedido> pedidos = pedidoAppHandler.getPedidosPorEstado(restauranteId, estado, page, size);

        return ResponseEntity.ok(pedidos);
    }
}
