package retoPragma.MicroPlazoleta.infrastructure.output.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstadoPedido;

import java.util.List;

@Entity
@Table(name = "pedido")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private Long idCliente;
    private Long idRestaurante;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItemEntity> items;
}
