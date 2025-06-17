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
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantIdResponseDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantSummaryResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IRestaurantAppHandler;
import retoPragma.MicroPlazoleta.domain.api.IRestaurantServicePort;
import retoPragma.MicroPlazoleta.domain.model.Order;

@RestController
@RequestMapping("/restaurantApp")
@RequiredArgsConstructor
public class RestaurantAppRestController {

    private final IRestaurantAppHandler restaurantAppHandler;
    private final IRestaurantServicePort restaurantServicePort;


    @Operation(summary = "Crear restaurante en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "restaurante creado con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })
    @PostMapping("/saveRestaurant")
    public ResponseEntity<Void> saveRestaurantInRestaurantApp(@RequestBody RestaurantAppRequestDto restaurantAppRequestDto) {
        restaurantAppHandler.saveRestaurantInRestaurantApp(restaurantAppRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Listar restaurante en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "listar restaurante con exito",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de response", content = @Content)

    })
    @GetMapping("/all")
    public ResponseEntity<PageResponseDto<RestaurantSummaryResponseDto>> listRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<RestaurantSummaryResponseDto> response = restaurantAppHandler.listRestaurants(page, size);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Obtener solo el ID del restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantIdResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{id}/restaurant")
    public ResponseEntity<RestaurantIdResponseDto> getRestaurantId(@PathVariable Long id) {
        Long restaurantId = restaurantAppHandler.getRestaurantIdByRestaurantId(id);
        return ResponseEntity.ok(new RestaurantIdResponseDto(restaurantId));
    }


}
