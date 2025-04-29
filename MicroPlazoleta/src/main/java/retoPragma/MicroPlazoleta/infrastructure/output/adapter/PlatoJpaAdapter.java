package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.NoPlatoException;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;


@RequiredArgsConstructor
public class PlatoJpaAdapter implements IPlatoPersistencePort {
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public void savePlato(Plato plato) {
        platoRepository.save(platoEntityMapper.toPlatoEntity(plato));
    }

    @Override
    public Plato findPlatoById(Long idPlato) {
        return platoRepository.findById(idPlato)
                .map(platoEntityMapper::toPlato)
                .orElseThrow(NoPlatoException::new);
    }
    @Override
    public Plato updateEstadoPlato(Long idPlato, boolean nuevoEstado, Long idUsuario) {
        var platoEntity = platoRepository.findById(idPlato)
                .orElseThrow(ExistecePlatoException::new) ;


        platoEntity.setEstado(nuevoEstado);


        platoRepository.save(platoEntity);


        return platoEntityMapper.toPlato(platoEntity);
    }
}
