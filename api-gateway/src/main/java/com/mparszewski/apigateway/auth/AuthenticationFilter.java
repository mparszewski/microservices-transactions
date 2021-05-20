package com.mparszewski.apigateway.auth;

import com.mparszewski.apigateway.routing.RouteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final RouteValidator routeValidator;

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {

            if (isAuthorizationMissing(request)) {
                return onError(exchange);
            }

            final String token = getAuthHeader(request);

            if (jwtService.isTokenExpired(token)) {
                return onError(exchange);
            }

            populateRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean isAuthorizationMissing(ServerHttpRequest serverHttpRequest) {
        return serverHttpRequest.getQueryParams().containsKey(AUTHORIZATION);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION).get(0);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        jwtService.getClaimsFromToken(token)
                .ifPresent(authUser -> {
                    exchange.getRequest().mutate()
                            .header("username", authUser.getUsername())
                            .header("email", authUser.getEmail())
                            .header("roles", authUser.getRoles().toArray(String[]::new))
                            .build();
                });
    }

}
