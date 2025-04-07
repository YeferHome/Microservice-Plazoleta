package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;

public class UsuarioUseCase {

    private final IUsuarioServicePort usuarioServicePort;

    public UsuarioUseCase(IUsuarioServicePort usuarioServicePort) {
        this.usuarioServicePort = usuarioServicePort;
    }


    public String validarRolUsuario(Long id){
        return usuarioServicePort.obtenerRolUsuario(id);
    }
}