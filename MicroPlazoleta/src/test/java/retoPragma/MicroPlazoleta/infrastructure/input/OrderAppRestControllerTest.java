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
import retoPragma.MicroPlazoleta.application.dto.*;
import retoPragma.MicroPlazoleta.application.handler.IOrderAppHandler;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;
import retoPragma.MicroPlazoleta.infrastructure.configuration.security.jwt.JwtService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderAppRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderAppRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderAppHandler orderAppHandler;

    @MockBean
    private JwtService jwtService;

    private ObjectMapper objectMapper;
    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        OrderItemRequestDto item = new OrderItemRequestDto(10L, 1L, 2);

        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setIdClient(5L);
        orderRequestDto.setIdRestaurant(1L);
        orderRequestDto.setItems(List.of(item));

        OrderItemResponseDto itemResponse = new OrderItemResponseDto(10L, 2);
        orderResponseDto = new OrderResponseDto(
                1L,
                EstateOrder.PENDIENTE,
                5L,
                1L,
                List.of(itemResponse),
                null,
                null
        );
    }

    @Test
    @WithMockUser
    void savePedido() throws Exception {
        Mockito.when(orderAppHandler.saveOrder(any(OrderRequestDto.class)))
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

        Mockito.when(orderAppHandler.getOrderByEstate(anyLong(), eq(EstateOrder.PENDIENTE), anyInt(), anyInt()))
                .thenReturn(responsePage);

        mockMvc.perform(get("/orderApp/estate")
                        .param("restaurantId", "1")
                        .param("estate", "PENDIENTE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void assignEmployeeAndSetInPreparation() throws Exception {
        Mockito.when(orderAppHandler.assignEmployeeAndSetInPreparation(anyLong(), anyLong()))
                .thenReturn(orderResponseDto);

        mockMvc.perform(put("/orderApp/asignEmployee/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void markOrderAsDone() throws Exception {
        Mockito.when(orderAppHandler.markOrderAsDone(anyLong(), anyString()))
                .thenReturn(orderResponseDto);

        mockMvc.perform(put("/orderApp/markOrderAsDone/1")
                        .header("Authorization", "Bearer token123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void markOrderAsDelivered() throws Exception {
        PinRequestDto pinRequest = new PinRequestDto("1234");

        Mockito.when(orderAppHandler.markOrderAsDelivered(anyLong(), eq("1234")))
                .thenReturn(orderResponseDto);

        mockMvc.perform(put("/orderApp/markOrderAsDelivered/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pinRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void cancelOrder() throws Exception {
        Mockito.when(jwtService.extractId("token123")).thenReturn(5L);
        Mockito.when(orderAppHandler.cancelOrder(1L, 5L)).thenReturn(orderResponseDto);

        mockMvc.perform(put("/orderApp/cancelOrder/1")
                        .header("Authorization", "Bearer token123"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void existsById() throws Exception {
        Mockito.when(orderAppHandler.existsById(1L)).thenReturn(true);

        mockMvc.perform(get("/orderApp/exists/1"))
                .andExpect(status().isOk());
    }
}
