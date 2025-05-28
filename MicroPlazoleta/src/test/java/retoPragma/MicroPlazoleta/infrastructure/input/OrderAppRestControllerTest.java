package retoPragma.MicroPlazoleta.infrastructure.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import retoPragma.MicroPlazoleta.application.dto.OrderItemRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderRequestDto;
import retoPragma.MicroPlazoleta.application.dto.OrderItemResponseDto;
import retoPragma.MicroPlazoleta.application.dto.OrderResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IOrderAppHandler;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderAppRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderAppRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderAppHandler pedidoAppHandler;

    private ObjectMapper objectMapper;

    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        OrderItemRequestDto item = new OrderItemRequestDto();
        item.setIdPlato(10L);
        item.setIdRestaurante(1L);
        item.setCantidad(2);

        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setIdCliente(5L);
        orderRequestDto.setIdRestaurante(1L);
        orderRequestDto.setItems(List.of(item));

        OrderItemResponseDto itemResponse = new OrderItemResponseDto(10L, 2);

        orderResponseDto = new OrderResponseDto(
                1L,
                EstateOrder.PENDIENTE,
                5L,
                1L,
                List.of(itemResponse)
        );
    }

    @Test
    @WithMockUser
    void savePedido() throws Exception {
        Mockito.when(pedidoAppHandler.saveOrder(any(OrderRequestDto.class)))
                .thenReturn(orderResponseDto);

        mockMvc.perform(post("/orderApp/saveOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void getPedidosPorEstado() throws Exception {
        PageResponseDto<OrderResponseDto> responsePage = new PageResponseDto<>(
                List.of(orderResponseDto), 0, 10, 1L
        );

        Mockito.when(pedidoAppHandler.getOrderByEstate(
                        anyLong(), eq(EstateOrder.PENDIENTE), anyInt(), anyInt()))
                .thenReturn(responsePage);

        mockMvc.perform(get("/orderApp/estate")
                        .param("restaurantId", "1")
                        .param("estate", "PENDIENTE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}
