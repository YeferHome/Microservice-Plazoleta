package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.NoPlatoException;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PlatoEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        platoRepository.save(platoEntityMapper.toPlatoEntity(dish));
    }

    @Override
    public Dish findDishById(Long idPlato) {
        return platoRepository.findById(idPlato)
                .map(platoEntityMapper::toPlato)
                .orElseThrow(NoPlatoException::new);
    }
    @Override
    public Dish updateEstateDish(Long idPlato, boolean nuevoEstado, Long idUsuario) {
        var platoEntity = platoRepository.findById(idPlato)
                .orElseThrow(ExistecePlatoException::new) ;


        platoEntity.setEstado(nuevoEstado);


        platoRepository.save(platoEntity);


        return platoEntityMapper.toPlato(platoEntity);
    }

    @Override
    public List<Dish> findByRestaurantAndCategory(Long idRestaurante, String categoria, int page, int size) {
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
    public List<Dish> findByRestaurant(Long idRestaurante, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlatoEntity> pageResult = platoRepository.findAllByIdRestaurante(idRestaurante, pageable);

        return pageResult.getContent()
                .stream()
                .map(platoEntityMapper::toPlato)
                .collect(Collectors.toList());
    }
}
