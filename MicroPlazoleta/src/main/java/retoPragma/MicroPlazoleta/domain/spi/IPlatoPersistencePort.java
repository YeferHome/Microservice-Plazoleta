package retoPragma.MicroPlazoleta.domain.spi;

import retoPragma.MicroPlazoleta.domain.model.Plato;

import java.util.List;

public interface IPlatoPersistencePort {
    void savePlato(Plato plato);
    Plato findPlatoById(Long idPlato);
    Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuario);
    List<Plato> findByRestauranteAndCategoria(Long idRestaurante, String categoria, int page, int size);
    List<Plato> findByRestaurante(Long idRestaurante, int page, int size);
}
