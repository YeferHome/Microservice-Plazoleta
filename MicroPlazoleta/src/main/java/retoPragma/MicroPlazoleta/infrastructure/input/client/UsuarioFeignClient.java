package retoPragma.MicroPlazoleta.infrastructure.input.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8080/usuarioApp")
public interface UsuarioFeignClient {

    @GetMapping("/{id}/rol")
    String obtenerRol(@PathVariable("id") Long id);


}
