package retoPragma.MicroPlazoleta.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retoPragma.MicroPlazoleta.domain.UseCase.PedidoUseCase;
import retoPragma.MicroPlazoleta.domain.UseCase.PlatoUseCase;
import retoPragma.MicroPlazoleta.domain.UseCase.RestauranteUseCase;
import retoPragma.MicroPlazoleta.domain.api.IPedidoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IPlatoServicePort;
import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUsuarioServicePort;
import retoPragma.MicroPlazoleta.domain.spi.IPedidoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IPlatoPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantePersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.PedidoJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.PlatoJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.RestauranteJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPedidoRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IPlatoRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestauranteRepository;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IUsuarioServicePort usuarioServicePort;
    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;

    @Bean
    public IPlatoPersistencePort platoPersistencePort() {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public IPlatoServicePort platoServicePort() {
        return new PlatoUseCase(platoPersistencePort(), restaurantePersistencePort(), usuarioServicePort);
    }

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(restauranteRepository, restauranteEntityMapper, platoRepository);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestauranteUseCase(restaurantePersistencePort(), usuarioServicePort);
    }

    @Bean
    public IPedidoPersistencePort pedidoPersistencePort() {
        return new PedidoJpaAdapter(pedidoRepository, pedidoEntityMapper);
    }

    @Bean
    public IPedidoServicePort pedidoServicePort() {
        return new PedidoUseCase(pedidoPersistencePort(), usuarioServicePort, restaurantePersistencePort());
    }
}
