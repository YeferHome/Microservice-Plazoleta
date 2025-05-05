package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Plato;

import java.util.List;

public interface IPlatoServicePort {
    void savePlato(Plato plato);
    Plato updatePlato(Long idPlato, Plato platoModificado, Long idUsuario);
    Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuario);
    List<Plato> getPlatosByRestaurante(Long idRestaurante, String categoria, int page, int size);
}