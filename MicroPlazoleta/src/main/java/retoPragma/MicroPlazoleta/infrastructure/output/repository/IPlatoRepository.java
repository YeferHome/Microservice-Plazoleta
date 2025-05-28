package retoPragma.MicroPlazoleta.infrastructure.output.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.DishEntity;

@Repository
public interface IPlatoRepository extends JpaRepository<DishEntity, Long> {
    Page<DishEntity> findAllByIdRestaurante(Long idRestaurante, Pageable pageable);
    Page<DishEntity> findAllByIdRestauranteAndCategoriaPlato(Long idRestaurante, String categoria, Pageable pageable);
    boolean existsByIdPlatoAndIdRestaurante(Long idPlato, Long idRestaurante);
}
