package retoPragma.MicroPlazoleta.infrastructure.input;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.handler.IPlatoAppHandler;

import java.util.List;

@RestController
@RequestMapping("/platoApp")
@RequiredArgsConstructor
public class PlatoAppRestController {

    private final IPlatoAppHandler platoAppHandler;

    @PostMapping("/savePlato")
    public ResponseEntity<Void> savePlatoInPlatoApp(@RequestBody PlatoAppRequestDto platoAppRequestDto) {
        platoAppHandler.savePlatoInPlatoApp(platoAppRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/modificarPlato/{idPlato}")
    public ResponseEntity<PlatoUpdateResponseDto> updatePlatoInPlatoApp(
            @PathVariable("idPlato") Long idPlato,
            @RequestBody PlatoUpdateRequestDto platoUpdateRequestDto) {

        PlatoUpdateResponseDto updatedPlato = platoAppHandler.updatePlatoInPlatoApp(idPlato, platoUpdateRequestDto);
        return ResponseEntity.ok(updatedPlato);
    }

    @PatchMapping("/{idPlato}/estado")
    public ResponseEntity<PlatoUpdateEstadoResponseDto> actualizarEstadoPlato(
            @PathVariable Long idPlato,
            @RequestBody PlatoUpdateEstadoRequestDto requestDto) {

        PlatoUpdateEstadoResponseDto updatedPlato =
                platoAppHandler.updateEstadoPlatoInPlatoApp(idPlato, requestDto.isEstado());
        return ResponseEntity.ok(updatedPlato);
    }

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
