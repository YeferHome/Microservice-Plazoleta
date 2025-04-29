package retoPragma.MicroPlazoleta.infrastructure.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import retoPragma.MicroPlazoleta.infrastructure.configuration.security.jwt.JwtAuthenticationFilter;
import retoPragma.MicroPlazoleta.infrastructure.configuration.security.jwt.JwtService;

@Configuration
public class SecurityConfiguration {

    private final JwtService jwtService;

    public SecurityConfiguration(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/restauranteApp/all",
                                "/platoApp/all",
                                "/platoApp/restaurantes/{idRestaurante}/menu",
                                "/pedidoApp/savePedido"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public static UserDetailsService userDetailsService() {
        return username -> {
            throw new RuntimeException("Not configured");
        };
    }
}
