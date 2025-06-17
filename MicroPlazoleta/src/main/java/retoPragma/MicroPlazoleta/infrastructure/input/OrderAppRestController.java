package retoPragma.MicroPlazoleta.infrastructure.input;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retoPragma.MicroPlazoleta.application.dto.OrderRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IOrderAppHandler;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

@RestController
@RequestMapping("/orderApp")
@RequiredArgsConstructor
public class OrderAppRestController {

    private final IOrderAppHandler orderAppHandler;

    @Operation(summary = "Crear pedido en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de solicitud", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/saveOrder")
    public ResponseEntity<OrderResponseDto> saveOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto responseDto = orderAppHandler.saveOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Obtener pedidos filtrando por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error en los parámetros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/estate")
    public ResponseEntity<PageResponseDto<OrderResponseDto>> getOrderByState(
            @RequestParam Long restaurantId,
            @RequestParam EstateOrder estate,
            @RequestParam int page,
            @RequestParam int size) {
        PageResponseDto<OrderResponseDto> responseDto = orderAppHandler.getOrderByEstate(restaurantId, estate, page, size);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Asignar pedido a Empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error en los parámetros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/asignEmployee/{orderId}/{employeeId}")
    public ResponseEntity<OrderResponseDto> assignEmployeeAndSetInPreparation(
            @PathVariable Long orderId,
            @PathVariable Long employeeId) {
        OrderResponseDto responseDto = orderAppHandler.assignEmployeeAndSetInPreparation(orderId, employeeId);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "Pedido hecho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden lista",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error en los parámetros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/markOrderAsDone/{orderId}")
    public ResponseEntity<OrderResponseDto> markOrderAsDone(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String token) {
        OrderResponseDto responseDto = orderAppHandler.markOrderAsDone(orderId, token);
        return ResponseEntity.ok(responseDto);

    }
}
