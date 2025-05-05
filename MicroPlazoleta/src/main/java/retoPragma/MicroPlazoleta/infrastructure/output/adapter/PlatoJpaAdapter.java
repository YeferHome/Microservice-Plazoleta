package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.exception.PlatoException.NoPlatoException;
import retoPragma.MicroPlazoleta.domain.model.Plato;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PlatoEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public List<Plato> findByRestauranteAndCategoria(Long idRestaurante, String categoria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlatoEntity> pageResult = platoRepository.findAllByIdRestauranteAndCategoriaPlato(
                idRestaurante, categoria, pageable
        );

        return pageResult.getContent()
                .stream()
                .map(platoEntityMapper::toPlato)
                .collect(Collectors.toList());
    }


    @Override
    public List<Plato> findByRestaurante(Long idRestaurante, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlatoEntity> pageResult = platoRepository.findAllByIdRestaurante(idRestaurante, pageable);

        return pageResult.getContent()
                .stream()
                .map(platoEntityMapper::toPlato)
                .collect(Collectors.toList());
    }
}
