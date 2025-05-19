package retoPragma.MicroPlazoleta.infrastructure.output.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import retoPragma.MicroPlazoleta.domain.model.Dish;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.ExistecePlatoException;
import retoPragma.MicroPlazoleta.domain.util.exception.PlatoException.NoPlatoException;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PlatoEntity;
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
    void savePlato() {
        Dish dish = mock(Dish.class);
        PlatoEntity platoEntity = mock(PlatoEntity.class);

        when(platoEntityMapper.toPlatoEntity(dish)).thenReturn(platoEntity);

        platoJpaAdapter.saveDish(dish);

        verify(platoEntityMapper).toPlatoEntity(dish);
        verify(platoRepository).save(platoEntity);
    }

    @Test
    void findPlatoById_success() {
        Long id = 1L;
        PlatoEntity platoEntity = mock(PlatoEntity.class);
        Dish dish = mock(Dish.class);

        when(platoRepository.findById(id)).thenReturn(Optional.of(platoEntity));
        when(platoEntityMapper.toPlato(platoEntity)).thenReturn(dish);

        Dish result = platoJpaAdapter.findDishById(id);

        verify(platoRepository).findById(id);
        verify(platoEntityMapper).toPlato(platoEntity);
        assertEquals(dish, result);
    }

    @Test
    void findPlatoById_notFound_throwsException() {
        Long id = 1L;

        when(platoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoPlatoException.class, () -> platoJpaAdapter.findDishById(id));

        verify(platoRepository).findById(id);
        verify(platoEntityMapper, never()).toPlato(any());
    }

    @Test
    void updateEstadoPlato_success() {
        Long idPlato = 1L;
        boolean nuevoEstado = true;
        Long idUsuario = 100L;

        PlatoEntity platoEntity = mock(PlatoEntity.class);
        Dish dish = mock(Dish.class);

        when(platoRepository.findById(idPlato)).thenReturn(Optional.of(platoEntity));
        when(platoEntityMapper.toPlato(platoEntity)).thenReturn(dish);

        Dish result = platoJpaAdapter.updateEstateDish(idPlato, nuevoEstado, idUsuario);

        verify(platoRepository).findById(idPlato);
        verify(platoEntity).setEstado(nuevoEstado);
        verify(platoRepository).save(platoEntity);
        verify(platoEntityMapper).toPlato(platoEntity);

        assertEquals(dish, result);
    }

    @Test
    void updateEstadoPlato_notFound_throwsException() {
        Long idPlato = 1L;
        boolean nuevoEstado = true;
        Long idUsuario = 100L;

        when(platoRepository.findById(idPlato)).thenReturn(Optional.empty());

        assertThrows(ExistecePlatoException.class,
                () -> platoJpaAdapter.updateEstateDish(idPlato, nuevoEstado, idUsuario));

        verify(platoRepository).findById(idPlato);
        verify(platoRepository, never()).save(any());
        verify(platoEntityMapper, never()).toPlato(any());
    }

    @Test
    void findByRestauranteAndCategoria() {
        Long idRestaurante = 1L;
        String categoria = "Categoria1";
        int page = 0;
        int size = 5;

        PlatoEntity platoEntity = mock(PlatoEntity.class);
        Dish dish = mock(Dish.class);

        List<PlatoEntity> entities = List.of(platoEntity);
        Page<PlatoEntity> pageResult = new PageImpl<>(entities);

        when(platoRepository.findAllByIdRestauranteAndCategoriaPlato(eq(idRestaurante), eq(categoria), any(Pageable.class)))
                .thenReturn(pageResult);

        when(platoEntityMapper.toPlato(platoEntity)).thenReturn(dish);

        List<Dish> result = platoJpaAdapter.findByRestaurantAndCategory(idRestaurante, categoria, page, size);

        verify(platoRepository).findAllByIdRestauranteAndCategoriaPlato(eq(idRestaurante), eq(categoria), any(Pageable.class));
        verify(platoEntityMapper).toPlato(platoEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dish, result.get(0));
    }

    @Test
    void findByRestaurante() {
        Long idRestaurante = 1L;
        int page = 0;
        int size = 5;

        PlatoEntity platoEntity = mock(PlatoEntity.class);
        Dish dish = mock(Dish.class);

        List<PlatoEntity> entities = List.of(platoEntity);
        Page<PlatoEntity> pageResult = new PageImpl<>(entities);

        when(platoRepository.findAllByIdRestaurante(eq(idRestaurante), any(Pageable.class)))
                .thenReturn(pageResult);

        when(platoEntityMapper.toPlato(platoEntity)).thenReturn(dish);

        List<Dish> result = platoJpaAdapter.findByRestaurant(idRestaurante, page, size);

        verify(platoRepository).findAllByIdRestaurante(eq(idRestaurante), any(Pageable.class));
        verify(platoEntityMapper).toPlato(platoEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dish, result.get(0));
    }
}
