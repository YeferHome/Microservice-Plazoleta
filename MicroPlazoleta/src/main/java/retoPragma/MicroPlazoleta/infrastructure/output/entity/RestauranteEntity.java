package retoPragma.MicroPlazoleta.infrastructure.output.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurante")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurante;

    private String nombreRestaurante;
    private Long nit;
    private String direccion;
    private Long telefonoRestaurante;
    private String urlLogo;
    private Long idUsuario;

}