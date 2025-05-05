package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlatoAppResponseDto {
    private String nombrePlato;
    private String descripcionPlato;
    private Long precioPlato;
    private String urlPlato;
    private String categoriaPlato;
    private Long idRestaurante;
    private Long idUsuario;
}
