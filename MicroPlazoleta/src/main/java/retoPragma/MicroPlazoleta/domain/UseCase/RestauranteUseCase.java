package retoPragma.MicroPlazoleta.domain.UseCase;


import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.exception.BusinessException;

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
            throw new RuntimeException("El usuario no tiene rol de propietario.");
        }

        if (restaurante.getNit() == null || restaurante.getNit() <= 0) {
            throw new BusinessException("Documento de identidad debe ser un número positivo.");
        }
        if (!esTelefonoRestauranteValido(restaurante.getTelefonoRestaurante())) {
            throw new BusinessException("Teléfono inválido; máximo 13 dígitos y debe iniciar con '+57'.");
        }

        if (!esNombreRestauranteValido(restaurante.getNombreRestaurante())) {
            throw new BusinessException("El nombre de el restaurante no puede ser solo numeros. Ejemplo:'Mi Restaurante 21'");
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