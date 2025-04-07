package retoPragma.MicroPlazoleta.infrastructure.input;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.handler.IRestauranteAppHandler;

@RestController
@RequestMapping("/restauranteApp")
@RequiredArgsConstructor
public class RestauranteAppRestController {

    private final IRestauranteAppHandler restauranteAppHandler;

    @PostMapping("/save")
    public ResponseEntity<Void> saveRestauranteInRestauranteApp(RestauranteAppRequestDto restauranteAppRequestDto){
        restauranteAppHandler.saveRestauranteInRestauranteApp(restauranteAppRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}