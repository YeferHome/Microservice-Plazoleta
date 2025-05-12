package retoPragma.MicroPlazoleta.infrastructure.input;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestauranteResumenResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IRestauranteAppHandler;

import java.util.List;

@RestController
@RequestMapping("/restauranteApp")
@RequiredArgsConstructor
public class RestauranteAppRestController {

    private final IRestauranteAppHandler restauranteAppHandler;

    @PostMapping("/saveRestaurante")
    public ResponseEntity<Void> saveRestauranteInRestauranteApp(@RequestBody RestauranteAppRequestDto restauranteAppRequestDto){
        restauranteAppHandler.saveRestauranteInRestauranteApp(restauranteAppRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestauranteResumenResponseDto>> listRestaurantes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<RestauranteResumenResponseDto> response = restauranteAppHandler.listRestaurantes(page, size);
        return ResponseEntity.ok(response);
    }
}