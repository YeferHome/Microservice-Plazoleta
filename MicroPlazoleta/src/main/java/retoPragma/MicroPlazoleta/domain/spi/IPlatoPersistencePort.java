package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Plato;

public interface IPlatoPersistencePort {
    void savePlato(Plato plato);
    Plato findPlatoById(Long idPlato);
}
