package retoPragma.MicroPlazoleta.infrastructure.output.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PlatoEntity;

@Repository
public interface IPlatoRepository extends JpaRepository<PlatoEntity, Long> {
}
