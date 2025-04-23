package retoPragma.MicroPlazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoAppRequestDto {

    private String nombrePlato;
    private String descripcionPlato;
    private Long precioPlato;
    private String urlPlato;
    private String categoriaPlato;
    private Long idRestaurante;
    private Long idUsuario;
}