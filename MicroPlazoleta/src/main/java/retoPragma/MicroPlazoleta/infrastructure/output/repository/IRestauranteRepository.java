package retoPragma.MicroPlazoleta.infrastructure.output.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestauranteEntity;

@Repository
public interface IRestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
}
