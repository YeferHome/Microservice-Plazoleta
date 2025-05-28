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
import retoPragma.MicroPlazoleta.application.dto.RestaurantAppRequestDto;
import retoPragma.MicroPlazoleta.application.dto.RestaurantSummaryResponseDto;
import retoPragma.MicroPlazoleta.application.dto.PageResponseDto;
import retoPragma.MicroPlazoleta.application.handler.IRestaurantAppHandler;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantAppRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestaurantAppRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRestaurantAppHandler restauranteAppHandler;

    private ObjectMapper objectMapper;

    private RestaurantAppRequestDto restaurantAppRequestDto;
    private PageResponseDto<RestaurantSummaryResponseDto> resumenResponseDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        restaurantAppRequestDto = new RestaurantAppRequestDto();

        RestaurantSummaryResponseDto resumen1 = new RestaurantSummaryResponseDto("Resto 1", "logo1.png");
        RestaurantSummaryResponseDto resumen2 = new RestaurantSummaryResponseDto("Resto 2", "logo2.png");

        resumenResponseDto = new PageResponseDto<>(
                List.of(resumen1, resumen2),
                0,  // pageNumber
                10, // pageSize
                2L  // totalElements
        );
    }

    @Test
    @WithMockUser
    void saveRestauranteInRestauranteApp() throws Exception {
        Mockito.doNothing().when(restauranteAppHandler).saveRestaurantInRestaurantApp(any(RestaurantAppRequestDto.class));

        mockMvc.perform(post("/restaurantApp/saveRestaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantAppRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void listRestaurantes() throws Exception {
        Mockito.when(restauranteAppHandler.listRestaurants(anyInt(), anyInt()))
                .thenReturn(resumenResponseDto);

        mockMvc.perform(get("/restaurantApp/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}
