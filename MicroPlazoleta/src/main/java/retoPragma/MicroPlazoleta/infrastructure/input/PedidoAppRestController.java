package retoPragma.MicroPlazoleta.infrastructure.input;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IPedidoAppHandler;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

@RestController
@RequestMapping("/pedidoApp")
@RequiredArgsConstructor
public class PedidoAppRestController {

    private final IPedidoAppHandler pedidoAppHandler;


    @Operation(summary = "Crear pedido en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })
    @PostMapping("/savePedido")
    public ResponseEntity<PedidoResponseDto> savePedido(@RequestBody PedidoRequestDto pedidoRequestDto) {
        PedidoResponseDto pedidoResponseDto = pedidoAppHandler.savePedido(pedidoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDto);
    }

    @Operation(summary = "Pedido actualizado en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido Actualizado con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })

    @GetMapping("/estado")
    public ResponseEntity<Page<Order>> getPedidosPorEstado(
            @RequestParam Long restauranteId,
            @RequestParam EstateOrder estado,
            @RequestParam  int page,
            @RequestParam int size) {

        Page<Order> pedidos = pedidoAppHandler.getPedidosPorEstado(restauranteId, estado, page, size);

        return ResponseEntity.ok(pedidos);
    }
}
