package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.model.PageModel;
import retoPragma.MicroPlazoleta.domain.model.PageRequestModel;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.NoPlatoException;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.DishEntity;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishJpaAdapterTest {

    private IPlatoRepository platoRepository;
    private IPlatoEntityMapper platoEntityMapper;
    private DishJpaAdapter platoJpaAdapter;

    @BeforeEach
    void setUp() {
        platoRepository = mock(IPlatoRepository.class);
        platoEntityMapper = mock(IPlatoEntityMapper.class);
        platoJpaAdapter = new DishJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Test
    void saveDish_success() {
        Dish dish = mock(Dish.class);
        DishEntity dishEntity = mock(DishEntity.class);

        when(platoEntityMapper.toPlatoEntity(dish)).thenReturn(dishEntity);
        platoJpaAdapter.saveDish(dish);

        verify(platoEntityMapper).toPlatoEntity(dish);
        verify(platoRepository).save(dishEntity);
    }

    @Test
    void findDishById_success() {
        Long id = 1L;
        DishEntity dishEntity = mock(DishEntity.class);
        Dish dish = mock(Dish.class);

        when(platoRepository.findById(id)).thenReturn(Optional.of(dishEntity));
        when(platoEntityMapper.toPlato(dishEntity)).thenReturn(dish);

        Dish result = platoJpaAdapter.findDishById(id);

        assertEquals(dish, result);
        verify(platoRepository).findById(id);
        verify(platoEntityMapper).toPlato(dishEntity);
    }

    @Test
    void findDishById_notFound_throws() {
        when(platoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoPlatoException.class, () -> platoJpaAdapter.findDishById(1L));
    }

    @Test
    void updateEstateDish_success() {
        Long id = 1L;
        DishEntity entity = mock(DishEntity.class);
        Dish expected = mock(Dish.class);

        when(platoRepository.findById(id)).thenReturn(Optional.of(entity));
        when(platoEntityMapper.toPlato(entity)).thenReturn(expected);

        Dish result = platoJpaAdapter.updateEstateDish(id, false, 10L);

        verify(entity).setEstate(false);
        verify(platoRepository).save(entity);
        assertEquals(expected, result);
    }

    @Test
    void updateEstateDish_notFound_throws() {
        when(platoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ExistecePlatoException.class, () -> platoJpaAdapter.updateEstateDish(1L, true, 10L));
    }

    @Test
    void findDishesByRestaurantAndOptionalCategory_withCategory() {
        Long restaurantId = 1L;
        String category = "Entradas";
        PageRequestModel request = new PageRequestModel(0, 5);

        DishEntity entity = mock(DishEntity.class);
        Dish dish = mock(Dish.class);
        Page<DishEntity> page = new PageImpl<>(List.of(entity));

        when(platoRepository.findAllByIdRestauranteAndCategoriaPlato(eq(restaurantId), eq(category), any(Pageable.class)))
                .thenReturn(page);
        when(platoEntityMapper.toPlato(entity)).thenReturn(dish);

        PageModel<Dish> result = platoJpaAdapter.findDishesByRestaurantAndOptionalCategory(restaurantId, category, request);

        assertEquals(1, result.getContent().size());
        assertEquals(dish, result.getContent().get(0));
    }

    @Test
    void findDishesByRestaurantAndOptionalCategory_withoutCategory() {
        Long restaurantId = 1L;
        PageRequestModel request = new PageRequestModel(0, 5);

        DishEntity entity = mock(DishEntity.class);
        Dish dish = mock(Dish.class);
        Page<DishEntity> page = new PageImpl<>(List.of(entity));

        when(platoRepository.findAllByIdRestaurante(eq(restaurantId), any(Pageable.class))).thenReturn(page);
        when(platoEntityMapper.toPlato(entity)).thenReturn(dish);

        PageModel<Dish> result = platoJpaAdapter.findDishesByRestaurantAndOptionalCategory(restaurantId, "", request);

        assertEquals(1, result.getContent().size());
        assertEquals(dish, result.getContent().get(0));
    }
}
