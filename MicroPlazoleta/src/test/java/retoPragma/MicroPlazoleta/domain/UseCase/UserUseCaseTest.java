package retoPragma.MicroPlazoleta.domain.UseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private IUserServicePort usuarioServicePort;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {

        usuarioServicePort = mock(IUserServicePort.class);
        userUseCase = new UserUseCase(usuarioServicePort);
    }

    @Test
    void validarRolUsuario() {

        Long userId = 1L;
        String rolEsperado = "PROPIETARIO";
        when(usuarioServicePort.obtainRolUser(userId)).thenReturn(rolEsperado);

        String rolObtenido = userUseCase.validateRolUser(userId);

        assertEquals(rolEsperado, rolObtenido);

        verify(usuarioServicePort, times(1)).obtainRolUser(userId);
    }
}
