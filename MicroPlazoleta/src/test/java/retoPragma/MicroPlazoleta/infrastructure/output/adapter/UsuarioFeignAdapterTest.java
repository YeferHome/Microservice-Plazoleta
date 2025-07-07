package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retoPragma.MicroPlazoleta.infrastructure.input.client.UsuarioFeignClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserFeignAdapterTest {

    private UsuarioFeignClient usuarioFeignClient;
    private UserFeignAdapter userFeignAdapter;

    @BeforeEach
    void setUp() {
        usuarioFeignClient = mock(UsuarioFeignClient.class);
        userFeignAdapter = new UserFeignAdapter(usuarioFeignClient);
    }

    @Test
    void obtainRolUser_shouldReturnRole() {
        Long userId = 123L;
        String expectedRole = "ADMIN";

        when(usuarioFeignClient.obtainRolUser(userId)).thenReturn(expectedRole);

        String actualRole = userFeignAdapter.obtainRolUser(userId);

        verify(usuarioFeignClient, times(1)).obtainRolUser(userId);
        assertEquals(expectedRole, actualRole);
    }

    @Test
    void obtainNumberPhoneClient_shouldReturnPhoneNumber() {
        Long clientId = 456L;
        String expectedPhone = "3216549870";

        when(usuarioFeignClient.obtainNumberPhone(clientId)).thenReturn(expectedPhone);

        String actualPhone = userFeignAdapter.obtainNumberPhoneClient(clientId);

        verify(usuarioFeignClient, times(1)).obtainNumberPhone(clientId);
        assertEquals(expectedPhone, actualPhone);
    }

    @Test
    void assignRestaurantToUser_shouldCallFeignClient() {
        Long userId = 789L;
        Long restaurantId = 10L;

        doNothing().when(usuarioFeignClient).assignRestaurantToUser(userId, restaurantId);

        userFeignAdapter.assignRestaurantToUser(userId, restaurantId);

        verify(usuarioFeignClient, times(1)).assignRestaurantToUser(userId, restaurantId);
    }
}
