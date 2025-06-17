package retoPragma.MicroPlazoleta.domain.api;

public interface IUserServicePort {
    String obtainRolUser(Long id);
    String obtainNumberPhoneClient(Long idClient);
    void assignRestaurantToUser(Long idUser, Long idRestaurant);
}