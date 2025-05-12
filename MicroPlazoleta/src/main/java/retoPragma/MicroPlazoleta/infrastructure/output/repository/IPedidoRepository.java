package retoPragma.MicroPlazoleta.infrastructure.output.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;
import retoPragma.MicroPlazoleta.infrastructure.output.entity.PedidoEntity;

import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long> {
    boolean existsByIdClienteAndEstadoIn(Long idCliente, List<EstadoPedido> estados);
    Page<PedidoEntity> findByEstadoAndIdRestaurante(EstadoPedido estado, Long restauranteId, PageRequest pageRequest);
}
