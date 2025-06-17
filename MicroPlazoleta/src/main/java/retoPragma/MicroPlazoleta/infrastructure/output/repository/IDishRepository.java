package retoPragma.MicroPlazoleta.infrastructure.output.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.DishEntity;

@Repository
public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Page<DishEntity> findAllByIdRestaurant(Long idRestaurant, Pageable pageable);
    Page<DishEntity> findAllByIdRestaurantAndCategoryDish(Long idRestaurant, String category, Pageable pageable);
    boolean existsByIdDishAndIdRestaurant(Long idDish, Long idRestaurant);
}
