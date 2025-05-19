package retoPragma.MicroPlazoleta.domain.util.restaurantUtil;

import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurant;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.DocumentException;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NameRestaurantException;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.NoOwnerException;
import retoPragma.MicroPlazoleta.domain.util.exception.RestaurantException.PhoneException;

import static retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RegexConstants.NOMBRE_VALIDO;
import static retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RegexConstants.NUMERO_VALIDO;
import static retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RestaurantConstants.DOCUMENTO_POSITIVO;
import static retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RestaurantConstants.PROPIETARIO;

public class RestaurantValidationUtil {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort usuarioServicePort;



    public RestaurantValidationUtil(IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort usuarioServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.usuarioServicePort = usuarioServicePort;
    }

    public void validateRestaurant(Restaurant restaurant) {


        String rol = usuarioServicePort.obtainRolUser(restaurant.getIdUsuario());
        if (!PROPIETARIO.equalsIgnoreCase(rol)) {
            throw new NoOwnerException();
        }

        if (restaurant.getNit() == null || restaurant.getNit() <= DOCUMENTO_POSITIVO) {
            throw new DocumentException();
        }
        if (!esTelefonoRestauranteValido(restaurant.getTelefonoRestaurante())) {
            throw new PhoneException();
        }

        if (!esNombreRestauranteValido(restaurant.getNombreRestaurante())) {
            throw new NameRestaurantException();
        }


    }

    public boolean esTelefonoRestauranteValido(String telefonoRestaurante) {
        if (telefonoRestaurante == null) return false;

        return telefonoRestaurante.matches(NUMERO_VALIDO);
    }

    public boolean esNombreRestauranteValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (nombre.matches(NOMBRE_VALIDO)) {
            return false;
        }
        return true;
    }

}
