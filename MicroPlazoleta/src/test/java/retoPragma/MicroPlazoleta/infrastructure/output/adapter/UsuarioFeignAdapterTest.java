package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.infrastructure.input.client.UsuarioFeignClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioFeignAdapterTest {

    private UsuarioFeignClient usuarioFeignClient;
    private UserFeignAdapter usuarioFeignAdapter;

    @BeforeEach
    void setUp() {
        usuarioFeignClient = mock(UsuarioFeignClient.class);
        usuarioFeignAdapter = new UserFeignAdapter(usuarioFeignClient);
    }

    @Test
    void obtenerRolUsuario() {
        Long userId = 123L;
        String expectedRole = "ADMIN";

        // Configurar el mock para devolver el role esperado
        when(usuarioFeignClient.obtenerRol(userId)).thenReturn(expectedRole);

        // Ejecutar el método a testear
        String actualRole = usuarioFeignAdapter.obtainRolUser(userId);

        // Verificar interacción y resultado
        verify(usuarioFeignClient, times(1)).obtenerRol(userId);
        assertEquals(expectedRole, actualRole);
    }
}
