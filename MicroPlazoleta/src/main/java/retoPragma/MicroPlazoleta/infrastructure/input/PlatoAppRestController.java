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
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.handler.IPlatoAppHandler;
import retoPragma.MicroPlazoleta.domain.model.Order;

import java.util.List;

@RestController
@RequestMapping("/platoApp")
@RequiredArgsConstructor
public class PlatoAppRestController {

    private final IPlatoAppHandler platoAppHandler;


    @Operation(summary = "Plato Creado en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato Creado con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })
    @PostMapping("/savePlato")
    public ResponseEntity<Void> savePlatoInPlatoApp(@RequestBody DishAppRequestDto dishAppRequestDto) {
        platoAppHandler.savePlatoInPlatoApp(dishAppRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "Plato actualizado en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato Actualizado con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })
    @PutMapping("/modificarPlato/{idPlato}")
    public ResponseEntity<PlatoUpdateResponseDto> updatePlatoInPlatoApp(
            @PathVariable("idPlato") Long idPlato,
            @RequestBody DishUpdateRequestDto dishUpdateRequestDto) {

        PlatoUpdateResponseDto updatedPlato = platoAppHandler.updatePlatoInPlatoApp(idPlato, dishUpdateRequestDto);
        return ResponseEntity.ok(updatedPlato);
    }
    @Operation(summary = "Estado Plato actualizado en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado plato Actualizado con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })
    @PatchMapping("/{idPlato}/estado")
    public ResponseEntity<PlatoUpdateEstadoResponseDto> actualizarEstadoPlato(
            @PathVariable Long idPlato,
            @RequestBody DishUpdateEstateRequestDto requestDto) {

        PlatoUpdateEstadoResponseDto updatedPlato =
                platoAppHandler.updateEstadoPlatoInPlatoApp(idPlato, requestDto.isEstado());
        return ResponseEntity.ok(updatedPlato);
    }

    @Operation(summary = "Listado de Menu de base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Menu con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })

    @GetMapping("/restaurantes/{idRestaurante}/menu")
    public ResponseEntity<List<PlatoAppResponseDto>> getMenuRestaurante(
            @PathVariable Long idRestaurante,
            @RequestParam(required = false) String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<PlatoAppResponseDto> response = platoAppHandler.listPlatosMenu(idRestaurante, categoria, page, size);
        return ResponseEntity.ok(response);
    }
}
