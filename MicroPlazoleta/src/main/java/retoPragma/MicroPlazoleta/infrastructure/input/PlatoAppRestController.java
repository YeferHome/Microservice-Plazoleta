package retoPragma.MicroPlazoleta.infrastructure.input;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.handler.IPlatoAppHandler;

@RestController
@RequestMapping("/platoApp")
@RequiredArgsConstructor
public class PlatoAppRestController {

    private final IPlatoAppHandler platoAppHandler;

    @PostMapping("/save")
    public ResponseEntity<Void> savePlatoInPlatoApp(@RequestBody PlatoAppRequestDto platoAppRequestDto){
        platoAppHandler.savePlatoInPlatoApp(platoAppRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PutMapping("/{idPlato}/{idUsuario}")
    public ResponseEntity<PlatoUpdateResponseDto> updatePlatoInPlatoApp(
            @PathVariable("idPlato") Long idPlato,
            @PathVariable("idUsuario") Long idUsuario,
            @RequestBody PlatoUpdateRequestDto platoUpdateRequestDto) {
        PlatoUpdateResponseDto updatedPlato = platoAppHandler.updatePlatoInPlatoApp(idPlato, platoUpdateRequestDto, idUsuario);
        return ResponseEntity.ok(updatedPlato);
    }


    @PatchMapping("/{idPlato}/estado/{idUsuario}")
    public ResponseEntity<PlatoUpdateEstadoResponseDto> actualizarEstadoPlato(
            @PathVariable Long idPlato,
            @PathVariable Long idUsuario,
            @RequestBody PlatoUpdateEstadoRequestDto requestDto) {


        PlatoUpdateEstadoResponseDto updatedPlato =
                platoAppHandler.updateEstadoPlatoInPlatoApp(idPlato, requestDto.isEstado(), idUsuario);
        return ResponseEntity.ok(updatedPlato);
    }

}