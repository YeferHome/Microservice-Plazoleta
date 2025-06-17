package retoPragma.MicroPlazoleta.infrastructure.output.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    private Long idClient;
    private Long idRestaurant;
    private Long employeeAssigned;

    @Enumerated(EnumType.STRING)
    private EstateOrder estate;
    private String pin;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
