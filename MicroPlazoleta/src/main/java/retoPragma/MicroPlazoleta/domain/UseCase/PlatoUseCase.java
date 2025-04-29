package retoPragma.MicroPlazoleta.domain.UseCase;


import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.*;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;

import static retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoConstants.PRECIO_MINIMO;
import static retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RestaurantConstants.PROPIETARIO;

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


        if (!PROPIETARIO.equalsIgnoreCase(usuarioServicePort.obtenerRolUsuario(plato.getIdUsuario()))) {
            throw new NoPermissionCreateException();
        }

        Restaurante restaurante = restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante());
        if (!restaurante.getIdUsuario().equals(plato.getIdUsuario())) {
            throw new PlatoOwnerException();
        }

            if (restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante()) == null) {
                throw new PlatoAssociatedException();
            }
            if (plato.getPrecioPlato() <= PRECIO_MINIMO) {
                throw new PricePlatoException();
            }

            plato.setEstado(true);

            platoPersistencePort.savePlato(plato);

    }
    @Override
    public Plato updatePlato(Long idPlato, Plato platoModificado, Long idUsuario) {

        String rol = usuarioServicePort.obtenerRolUsuario(idUsuario);
        if (!PROPIETARIO.equalsIgnoreCase(rol)) {
            throw new permissionPlatoException();
        }

        Plato platoUpdate = platoPersistencePort.findPlatoById(idPlato);
        if (platoUpdate == null) {
            throw new ExistecePlatoException();
        }

        Restaurante restaurante = restaurantePersistencePort.findRestauranteById(platoUpdate.getIdRestaurante());
        if (restaurante == null) {
            throw new associationExistenceException();
        }

        if (!restaurante.getIdUsuario().equals(idUsuario)) {
            throw new OwnerNoRestaurantException();
        }

        platoUpdate.setDescripcionPlato(platoModificado.getDescripcionPlato());
        platoUpdate.setPrecioPlato(platoModificado.getPrecioPlato());
        platoPersistencePort.savePlato(platoUpdate);
        return platoUpdate;
    }

    @Override
    public Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuario) {

        String rol = usuarioServicePort.obtenerRolUsuario(idUsuario);
        if (!PROPIETARIO.equalsIgnoreCase(rol)) {
            throw new NoPermissionEstadoExcepcion();
        }


        Plato plato = platoPersistencePort.findPlatoById(idPlato);
        if (plato == null) {
            throw new NoPlatoException();
        }


        Restaurante restaurante = restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante());
        if (restaurante == null) {
            throw new RestaurantNoPermissionExcepcion();
        }


        if (!restaurante.getIdUsuario().equals(idUsuario)) {
            throw new OwnerNoRestaurantException();
        }


        plato.setEstado(nuevoEstado);
        platoPersistencePort.savePlato(plato);

        return plato;
    }
 }