package retoPragma.MicroPlazoleta.infrastructure.output.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.OrderEntity;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByIdClientAndEstateIn(Long idClient, List<EstateOrder> estate);

    Page<OrderEntity> findByEstateAndIdRestaurant(EstateOrder estate, Long restaurantId, Pageable pageable);
}
