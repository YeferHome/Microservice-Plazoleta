package retoPragma.MicroPlazoleta.infrastructure.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoRequestDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoItemResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PedidoResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IPedidoAppHandler;
import retoPragma.MicroPlazoleta.domain.model.Order;
import retoPragma.MicroPlazoleta.domain.util.pedidoUtil.EstateOrder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoAppRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderAppRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPedidoAppHandler pedidoAppHandler;

    private ObjectMapper objectMapper;

    private PedidoRequestDto pedidoRequestDto;
    private PedidoResponseDto pedidoResponseDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        PedidoItemRequestDto item = new PedidoItemRequestDto();
        item.setIdPlato(10L);
        item.setIdRestaurante(1L);
        item.setCantidad(2);

        pedidoRequestDto = new PedidoRequestDto();
        pedidoRequestDto.setIdCliente(5L);
        pedidoRequestDto.setIdRestaurante(1L);
        pedidoRequestDto.setItems(List.of(item));

        PedidoItemResponseDto itemResponse = new PedidoItemResponseDto(10L, 2);

        pedidoResponseDto = new PedidoResponseDto(
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
        Mockito.when(pedidoAppHandler.savePedido(any(PedidoRequestDto.class)))
                .thenReturn(pedidoResponseDto);

        mockMvc.perform(post("/pedidoApp/savePedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void getPedidosPorEstado() throws Exception {
        Order orderMock = new Order();
        // Puedes configurar pedidoMock con valores si quieres.

        Page<Order> pedidoPage = new PageImpl<>(List.of(orderMock), PageRequest.of(0, 10), 1);

        Mockito.when(pedidoAppHandler.getPedidosPorEstado(
                        anyLong(),
                        eq(EstateOrder.PENDIENTE),
                        anyInt(),
                        anyInt()))
                .thenReturn(pedidoPage);

        mockMvc.perform(get("/pedidoApp/estado")
                        .param("restauranteId", "1")
                        .param("estado", "PENDIENTE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}
