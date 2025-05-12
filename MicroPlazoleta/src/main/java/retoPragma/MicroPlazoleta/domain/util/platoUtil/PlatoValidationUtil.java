package retoPragma.MicroPlazoleta.domain.util.platoUtil;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.*;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;

import static retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoConstants.PRECIO_MINIMO;
import static retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoConstants.PROPIETARIO;

public class PlatoValidationUtil {

        private final IUsuarioServicePort usuarioServicePort;
        private final IRestaurantePersistencePort restaurantePersistencePort;
        private final IPlatoPersistencePort platoPersistencePort;


    public PlatoValidationUtil(IUsuarioServicePort usuarioServicePort, IRestaurantePersistencePort restaurantePersistencePort, IPlatoPersistencePort platoPersistencePort) {
        this.usuarioServicePort = usuarioServicePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
    }

    public void validarPlato(Plato plato) {

            Restaurante restaurante = restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante());
            if (restaurante == null) {
                throw new PlatoAssociatedException();
            }

            if (!restaurante.getIdUsuario().equals(plato.getIdUsuario())) {
                throw new PlatoOwnerException();
            }

            if (plato.getPrecioPlato() <= PRECIO_MINIMO) {
                throw new PricePlatoException();
            }
        }

    public void validarPlatoExistente(Long idPlato) {
        Plato plato = platoPersistencePort.findPlatoById(idPlato);
        if (plato == null) {
            throw new ExistecePlatoException();
        }
    }

    public void validarPropietario(Plato plato,  Long idUsuarioToken) {

        String rolUsuario = usuarioServicePort.obtenerRolUsuario(plato.getIdUsuario());
        if (!PROPIETARIO.equalsIgnoreCase(rolUsuario)) {
            throw new NoPermissionCreateException();
        }
        Restaurante restaurante = restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante());
        if (!restaurante.getIdUsuario().equals(idUsuarioToken)) {
            throw new OwnerNoRestaurantException();
        }
    }

}
