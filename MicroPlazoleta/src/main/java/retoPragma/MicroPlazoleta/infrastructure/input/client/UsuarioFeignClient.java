package retoPragma.MicroPlazoleta.infrastructure.input.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import retoPragma.MicroPlazoleta.infrastructure.configuration.feing.FeignClientConfig;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8081/usuarioApp", configuration = FeignClientConfig.class)
public interface UsuarioFeignClient {

    @GetMapping("/{id}/rol")
    String obtenerRol(@PathVariable("id") Long id);

    @GetMapping("/propietario/{idRestaurante}")
    Long obtenerIdPropietarioPorRestaurante(@PathVariable("idRestaurante") Long idRestaurante);
}
