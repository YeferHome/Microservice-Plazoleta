package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.infrastructure.input.client.UsuarioFeignClient;


@Service
@AllArgsConstructor
public class UserFeignAdapter implements IUserServicePort {

    private final UsuarioFeignClient usuarioFeignClient;

    @Override
    public String obtainRolUser(Long id) {
        return usuarioFeignClient.obtainRolUser(id);
    }

    @Override
    public String obtainNumberPhoneClient(Long idClient) {
        return usuarioFeignClient.obtainNumberPhone(idClient);
    }
    @Override
    public void assignRestaurantToUser(Long idUser, Long idRestaurant) {
        usuarioFeignClient.assignRestaurantToUser(idUser, idRestaurant);
    }
}