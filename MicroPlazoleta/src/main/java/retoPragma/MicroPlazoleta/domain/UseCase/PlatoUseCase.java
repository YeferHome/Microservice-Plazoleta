package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.*;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.model.Restaurante;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;

import java.util.List;

import static retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoConstants.PRECIO_MINIMO;
import static retoPragma.MicroPlazoleta.domain.util.restaurantUtil.RestaurantConstants.*;

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
        if (restaurante == null) {
            throw new PlatoAssociatedException();
        }

        if (!restaurante.getIdUsuario().equals(plato.getIdUsuario())) {
            throw new PlatoOwnerException();
        }

        if (plato.getPrecioPlato() <= PRECIO_MINIMO) {
            throw new PricePlatoException();
        }

        plato.setEstado(true);
        platoPersistencePort.savePlato(plato);
    }

    @Override
    public Plato updatePlato(Long idPlato, Plato platoModificado, Long idUsuarioToken) {
        String rol = usuarioServicePort.obtenerRolUsuario(idUsuarioToken);
        if (!PROPIETARIO.equalsIgnoreCase(rol)) {
            throw new permissionPlatoException();
        }

        Plato plato = platoPersistencePort.findPlatoById(idPlato);
        if (plato == null) {
            throw new ExistecePlatoException();
        }

        Restaurante restaurante = restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante());
        if (restaurante == null) {
            throw new associationExistenceException();
        }

        if (!restaurante.getIdUsuario().equals(idUsuarioToken)) {
            throw new OwnerNoRestaurantException();
        }

        plato.setDescripcionPlato(platoModificado.getDescripcionPlato());
        plato.setPrecioPlato(platoModificado.getPrecioPlato());
        platoPersistencePort.savePlato(plato);
        return plato;
    }

    @Override
    public Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuarioToken) {
        String rol = usuarioServicePort.obtenerRolUsuario(idUsuarioToken);
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

        if (!restaurante.getIdUsuario().equals(idUsuarioToken)) {
            throw new OwnerNoRestaurantException();
        }

        plato.setEstado(nuevoEstado);
        platoPersistencePort.savePlato(plato);
        return plato;
    }

    @Override
    public List<Plato> getPlatosByRestaurante(Long idRestaurante, String categoria, int page, int size) {
        if (categoria != null && !categoria.trim().isEmpty()) {
            return platoPersistencePort.findByRestauranteAndCategoria(idRestaurante, categoria, page, size);
        } else {
            return platoPersistencePort.findByRestaurante(idRestaurante, page, size);
        }
    }
}
