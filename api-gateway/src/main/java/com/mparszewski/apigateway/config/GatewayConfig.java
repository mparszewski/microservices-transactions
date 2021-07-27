package com.mparszewski.apigateway.config;

import com.mparszewski.apigateway.auth.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RouteLocator routing(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()

                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://auth"))

                .route("warehouse-service", r -> r.path("/warehouse/**")
                        .filters(f -> f.filter(authenticationFilter)
                                .rewritePath("/warehouse/(?<segment>.*)", "/warehouse/${segment}"))
                        .uri("lb://warehouse"))

                .route("order-service", r -> r.path("/order/**")
                        .filters(f -> f.filter(authenticationFilter)
                                .rewritePath("/order/(?<segment>.*)", "/order/${segment}"))
                        .uri("lb://order"))

                .build();
    }
}
