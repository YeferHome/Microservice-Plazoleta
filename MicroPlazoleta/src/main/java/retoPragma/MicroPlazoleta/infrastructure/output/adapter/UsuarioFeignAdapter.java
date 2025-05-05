package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.infrastructure.input.UsuarioFeignClient;


@Service
@AllArgsConstructor
public class UsuarioFeignAdapter implements IUsuarioServicePort {

    private final UsuarioFeignClient usuarioFeignClient;

    @Override
    public String obtenerRolUsuario(Long id) {
        return usuarioFeignClient.obtenerRol(id);
    }
}