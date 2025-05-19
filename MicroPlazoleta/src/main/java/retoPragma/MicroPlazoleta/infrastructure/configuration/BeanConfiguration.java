package retoPragma.MicroPlazoleta.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retoPragma.MicroPlazoleta.domain.UseCase.OrderUseCase;
import retoPragma.MicroPlazoleta.domain.UseCase.DishUseCase;
import retoPragma.MicroPlazoleta.domain.UseCase.RestaurantUseCase;
import retoPragma.MicroPlazoleta.domain.api.IOrderServicePort;
import retoPragma.MicroPlazoleta.domain.api.IDishServicePort;
import retoPragma.MicroPlazoleta.domain.api.IRestauranteServicePort;
import retoPragma.MicroPlazoleta.domain.api.IUserServicePort;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.OrderJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.DishJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.RestaurantJpaAdapter;
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
    private final IUserServicePort usuarioServicePort;
    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;

    @Bean
    public IDishPersistencePort platoPersistencePort() {
        return new DishJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public IDishServicePort platoServicePort() {
        return new DishUseCase(platoPersistencePort(), restaurantePersistencePort(), usuarioServicePort);
    }

    @Bean
    public IRestaurantPersistencePort restaurantePersistencePort() {
        return new RestaurantJpaAdapter(restauranteRepository, restauranteEntityMapper, platoRepository);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestaurantUseCase(restaurantePersistencePort(), usuarioServicePort);
    }

    @Bean
    public IOrderPersistencePort pedidoPersistencePort() {
        return new OrderJpaAdapter(pedidoRepository, pedidoEntityMapper);
    }

    @Bean
    public IOrderServicePort pedidoServicePort() {
        return new OrderUseCase(pedidoPersistencePort(), usuarioServicePort, restaurantePersistencePort());
    }
}
