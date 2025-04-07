package retoPragma.MicroPlazoleta.infrastructure.output.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.RestauranteEntity;

public interface IRestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
}
