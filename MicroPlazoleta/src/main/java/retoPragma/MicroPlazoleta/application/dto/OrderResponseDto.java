package retoPragma.MicroPlazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {

    private Long idOrder;
    private EstateOrder estate;
    private Long idClient;
    private Long idRestaurant;
    private List<OrderItemResponseDto> items;
    private Long employeeAssigned;
    private String pin;

}
