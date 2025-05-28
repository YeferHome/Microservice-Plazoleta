package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.DishEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.NoPlatoException;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        platoRepository.save(platoEntityMapper.toPlatoEntity(dish));
    }

    @Override
    public Dish findDishById(Long idDish) {
        return platoRepository.findById(idDish)
                .map(platoEntityMapper::toPlato)
                .orElseThrow(NoPlatoException::new);
    }

    @Override
    public Dish updateEstateDish(Long idDish, boolean newState, Long idUser) {
        var platoEntity = platoRepository.findById(idDish)
                .orElseThrow(ExistecePlatoException::new);

        platoEntity.setEstate(newState);
        platoRepository.save(platoEntity);

        return platoEntityMapper.toPlato(platoEntity);
    }

    @Override
    public PageModel<Dish> findDishesByRestaurantAndOptionalCategory(Long idRestaurant, String category, PageRequestModel pageRequestModel) {
        Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());

        Page<DishEntity> pageResult;
        if (category != null && !category.isBlank()) {
            pageResult = platoRepository.findAllByIdRestauranteAndCategoriaPlato(idRestaurant, category, pageable);
        } else {
            pageResult = platoRepository.findAllByIdRestaurante(idRestaurant, pageable);
        }

        List<Dish> content = pageResult.getContent().stream()
                .map(platoEntityMapper::toPlato)
                .toList();

        return new PageModel<>(content, pageResult.getNumber(), pageResult.getSize(), pageResult.getTotalElements());
    }
}
