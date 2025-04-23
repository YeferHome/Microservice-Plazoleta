package retoPragma.MicroPlazoleta.domain.UseCase;


import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.NoPermissionCreateException;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.PlatoAssociatedException;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.PricePlatoException;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioServicePort usuarioServicePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IUsuarioServicePort usuarioServicePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioServicePort = usuarioServicePort;
    }


    @Override
    public void savePlato(Plato plato) {

        if (!"PROPIETARIO".equalsIgnoreCase(usuarioServicePort.obtenerRolUsuario(plato.getIdUsuario()))) {
            throw new NoPermissionCreateException();
        }


        if (restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante()) == null) {
            throw new PlatoAssociatedException();
        }
        if (plato.getPrecioPlato() <= 0) {
            throw new PricePlatoException();
        }

        plato.setActivoPlato(true);

        platoPersistencePort.savePlato(plato);
    }

}