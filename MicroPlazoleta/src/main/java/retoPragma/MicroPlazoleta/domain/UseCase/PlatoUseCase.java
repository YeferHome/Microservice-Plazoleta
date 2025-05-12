package retoPragma.MicroPlazoleta.domain.UseCase;

import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.*;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;
import retoPragma.MicroPlazoleta.domain.util.platoUtil.PlatoValidationUtil;

import java.util.List;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioServicePort usuarioServicePort;
    private final PlatoValidationUtil platoValidationUtil;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IUsuarioServicePort usuarioServicePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioServicePort = usuarioServicePort;
        this.platoValidationUtil = new PlatoValidationUtil(usuarioServicePort, restaurantePersistencePort, platoPersistencePort);
    }

    @Override
    public void savePlato(Plato plato) {

        platoValidationUtil.validarPlato(plato);
        plato.setEstado(true);
        platoPersistencePort.savePlato(plato);
    }

    @Override
    public Plato updatePlato(Long idPlato, Plato platoModificado, Long idUsuarioToken) {

        platoValidationUtil.validarPropietario(platoModificado, idUsuarioToken);
        platoValidationUtil.validarPlato(platoModificado);
        platoValidationUtil.validarPlatoExistente(idPlato);

        Plato plato = platoPersistencePort.findPlatoById(idPlato);

        if (plato == null) {
            throw new ExistecePlatoException();
        }

        plato.setDescripcionPlato(platoModificado.getDescripcionPlato());
        plato.setPrecioPlato(platoModificado.getPrecioPlato());
        platoPersistencePort.savePlato(plato);
        return plato;
    }

    @Override
    public Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuarioToken) {

        platoValidationUtil.validarPlatoExistente(idPlato);
        platoValidationUtil.validarPropietario(new Plato(), idUsuarioToken);

        Plato plato = platoPersistencePort.findPlatoById(idPlato);

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
