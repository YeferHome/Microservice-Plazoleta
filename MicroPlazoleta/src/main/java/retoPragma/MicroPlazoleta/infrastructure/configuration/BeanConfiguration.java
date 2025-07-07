package retoPragma.MicroPlazoleta.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retoPragma.MicroPlazoleta.domain.UseCase.DishUseCase;
import retoPragma.MicroPlazoleta.domain.UseCase.OrderUseCase;
import retoPragma.MicroPlazoleta.domain.UseCase.RestaurantUseCase;
import retoPragma.MicroPlazoleta.domain.api.*;
import retoPragma.MicroPlazoleta.domain.spi.IDishPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IOrderPersistencePort;
import retoPragma.MicroPlazoleta.domain.spi.IRestaurantPersistencePort;
import retoPragma.MicroPlazoleta.infrastructure.input.client.TraceabilityFeignClient;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.DishJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.OrderJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.RestaurantJpaAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.adapter.TraceabilityAdapter;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPlatoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IPedidoEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.IRestauranteEntityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.mapper.ITraceabilityMapper;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IDishRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IOrderRepository;
import retoPragma.MicroPlazoleta.infrastructure.output.repository.IRestaurantRepository;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IDishRepository dishRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IOrderRepository orderRepository;

    private final IPlatoEntityMapper dishEntityMapper;
    private final IRestauranteEntityMapper restaurantEntityMapper;
    private final IPedidoEntityMapper orderEntityMapper;

    private final IUserServicePort userServicePort;
    private final IMessagingServicePort messagingServicePort;

    private final TraceabilityFeignClient traceabilityFeignClient;
    private final ITraceabilityMapper traceabilityRequestMapper;

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper, dishRepository);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort(), userServicePort);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userServicePort);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityAdapter(traceabilityFeignClient, traceabilityRequestMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), userServicePort, restaurantPersistencePort(), messagingServicePort, traceabilityServicePort());
    }
}
