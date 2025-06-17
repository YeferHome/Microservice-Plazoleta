package retoPragma.MicroPlazoleta.infrastructure.output.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestaurantEntity;

import java.util.Optional;

@Repository
public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findTopByIdUserOrderByIdRestaurantDesc(Long idUser);
    Optional<RestaurantEntity> findByIdUser(Long idUser);

}
