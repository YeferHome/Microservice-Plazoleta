package retoPragma.MicroPlazoleta.infrastructure.output.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoEntity;

import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long> {
    boolean existsByIdClienteAndEstadoIn(Long idCliente, List<EstateOrder> estados);
    Page<PedidoEntity> findByEstadoAndIdRestaurante(EstateOrder estado, Long restauranteId, int page, int size);
}
