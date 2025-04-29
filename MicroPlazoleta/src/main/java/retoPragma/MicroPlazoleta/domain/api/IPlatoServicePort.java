package retoPragma.MicroPlazoleta.domain.api;

import retoPragma.MicroPlazoleta.domain.model.Plato;

public interface IPlatoServicePort {
    void savePlato(Plato plato);
    Plato updatePlato(Long idPlato, Plato platoModificado, Long idUsuario);
    Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuario);
}