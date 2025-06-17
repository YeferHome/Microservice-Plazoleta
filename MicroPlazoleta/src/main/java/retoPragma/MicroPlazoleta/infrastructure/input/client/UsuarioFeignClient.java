package retoPragma.MicroPlazoleta.infrastructure.input.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import retoPragma.MicroPlazoleta.infrastructure.configuration.feing.FeignClientConfig;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8081/userApp", configuration = FeignClientConfig.class)
public interface UsuarioFeignClient {

    @GetMapping("/{id}/rol")
    String obtainRolUser(@PathVariable("id") Long id);

    @GetMapping("/owner/{idRestaurant}")
    Long obtenerIdPropietarioPorRestaurante(@PathVariable("idRestaurante") Long idRestaurante);

    @GetMapping("/users/{id}/numberPhone")
    String obtainNumberPhone(@PathVariable("id") Long id);

    @PutMapping("/update/{userId}/restaurant/{restaurantId}")
    void assignRestaurantToUser(@PathVariable("userId") Long userId, @PathVariable("restaurantId") Long restaurantId);

}
