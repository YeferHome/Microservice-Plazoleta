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
import retoPragma.MicroPlazoleta.application.dto.RestauranteAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestauranteResumenResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IRestauranteAppHandler;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestauranteAppRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantAppRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRestauranteAppHandler restauranteAppHandler;

    private ObjectMapper objectMapper;

    private RestauranteAppRequestDto restauranteAppRequestDto;
    private List<RestauranteResumenResponseDto> resumenResponseList;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        restauranteAppRequestDto = new RestauranteAppRequestDto();
        // Configura aquí los campos necesarios para el requestDto
        // Ejemplo:
        // restauranteAppRequestDto.setNombre("Restaurante Prueba");

        RestauranteResumenResponseDto resumen = new RestauranteResumenResponseDto();
        // Configura campos necesarios en resumen, por ejemplo nombre, id, etc.
        // resumen.setNombre("Restaurante 1");

        resumenResponseList = List.of(resumen);
    }

    @Test
    @WithMockUser
    void saveRestauranteInRestauranteApp() throws Exception {
        Mockito.doNothing().when(restauranteAppHandler).saveRestauranteInRestauranteApp(any(RestauranteAppRequestDto.class));

        mockMvc.perform(post("/restauranteApp/saveRestaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteAppRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void listRestaurantes() throws Exception {
        Mockito.when(restauranteAppHandler.listRestaurantes(anyInt(), anyInt()))
                .thenReturn(resumenResponseList);

        mockMvc.perform(get("/restauranteApp/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}
