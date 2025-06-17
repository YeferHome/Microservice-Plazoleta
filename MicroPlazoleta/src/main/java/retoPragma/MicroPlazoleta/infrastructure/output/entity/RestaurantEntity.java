package retoPragma.MicroPlazoleta.infrastructure.output.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idRestaurant;

    private String nameRestaurant;
    private Long nit;
    private String address;
    private Long phoneRestaurant;
    private String urlLogo;
    private Long idUser;

}