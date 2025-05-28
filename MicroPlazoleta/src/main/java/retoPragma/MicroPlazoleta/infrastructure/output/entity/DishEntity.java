package retoPragma.MicroPlazoleta.infrastructure.output.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDish;

    private String nameDish;
    private String descriptionDish;
    private Long priceDish;
    private String urlDish;
    private String categoryDish;
    private Boolean estate;
    private Long idRestaurant;

}