package retoPragma.MicroPlazoleta.domain.UseCase;


import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.RestaurantException.DocumentException;
import retoPragma.MicroPlazoleta.domain.exception.RestaurantException.NameRestaurantException;
import retoPragma.MicroPlazoleta.domain.exception.RestaurantException.NoOwnerException;
import retoPragma.MicroPlazoleta.domain.exception.RestaurantException.PhoneException;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;

import java.util.Optional;

public class RestauranteUseCase implements IRestauranteServicePort {

    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioServicePort usuarioServicePort;

    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IUsuarioServicePort usuarioServicePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioServicePort = usuarioServicePort;
    }

    @Override
    public void saveRestaurante(Restaurante restaurante) {

        String rol = usuarioServicePort.obtenerRolUsuario(restaurante.getIdUsuario());
        if (!"PROPIETARIO".equalsIgnoreCase(rol)) {
            throw new NoOwnerException();
        }

        if (restaurante.getNit() == null || restaurante.getNit() <= 0) {
            throw new DocumentException();
        }
        if (!esTelefonoRestauranteValido(restaurante.getTelefonoRestaurante())) {
            throw new PhoneException();
        }

        if (!esNombreRestauranteValido(restaurante.getNombreRestaurante())) {
            throw new NameRestaurantException();
        }
        restaurantePersistencePort.saveRestaurante(restaurante);
    }

    @Override
    public Optional<Restaurante> findRestauranteById(Long id) {
        return Optional.empty();
    }


    private boolean esTelefonoRestauranteValido(String telefonoRestaurante) {
        if (telefonoRestaurante == null) return false;

        return telefonoRestaurante.matches("\\+?\\d{1,13}");
    }

    private boolean esNombreRestauranteValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (nombre.matches("\\d+")) {
            return false;
        }
        return true;
    }

}