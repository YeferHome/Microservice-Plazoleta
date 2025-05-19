package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;

public class UserUseCase {

    private final IUserServicePort userServicePort;

    public UserUseCase(IUserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    public String validateRolUser(Long id){
        return userServicePort.obtainRolUser(id);
    }
}