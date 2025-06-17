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
import retoPragma.MicroPlazoleta.application.handler.IDishAppHandler;
import retoPragma.MicroPlazoleta.domain.model.Order;

@RestController
@RequestMapping("/DishApp")
@RequiredArgsConstructor
public class DishAppRestController {

    private final IDishAppHandler platoAppHandler;


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
    @PostMapping("/saveDish")
    public ResponseEntity<Void> saveDishInDishApp(@RequestBody DishAppRequestDto dishAppRequestDto) {
        platoAppHandler.saveDishInDishApp(dishAppRequestDto);
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
    @PutMapping("/modifyDish/{idDish}")
    public ResponseEntity<DishUpdateResponseDto> updateDishInDishApp(
            @PathVariable("idDish") Long idDish,
            @RequestBody DishUpdateRequestDto dishUpdateRequestDto) {

        DishUpdateResponseDto updatedPlato = platoAppHandler.updateDishInDishApp(idDish, dishUpdateRequestDto);
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
    @PatchMapping("/{idDish}/estate")
    public ResponseEntity<DishUpdateEstateResponseDto> updateEstateDish(
            @PathVariable Long idDish,
            @RequestBody DishUpdateEstateRequestDto requestDto) {

        DishUpdateEstateResponseDto updatedPlato =
                platoAppHandler.updateEstateDishInDishApp(idDish, requestDto.isEstate());
        return ResponseEntity.ok(updatedPlato);
    }

    @Operation(summary = "Listado de menú paginado y filtrado por categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishAppResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error en los parámetros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content)
    })
    @GetMapping("/restaurants/{idRestaurant}/menu")
    public ResponseEntity<PageResponseDto<DishAppResponseDto>> getMenuRestaurant(
            @PathVariable Long idRestaurant,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponseDto<DishAppResponseDto> response =
                platoAppHandler.listDishMenu(idRestaurant, category, page, size);

        return ResponseEntity.ok(response);
    }
}
