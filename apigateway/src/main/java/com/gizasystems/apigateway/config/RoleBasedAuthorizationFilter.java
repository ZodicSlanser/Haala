package com.gizasystems.apigateway.config;

import com.gizasystems.apigateway.config.properties.AuthorizedEndpointsConfig;
import com.gizasystems.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class RoleBasedAuthorizationFilter {
    private final JwtUtil jwtUtils;
    private final AuthorizedEndpointsConfig authorizedEndpointsConfig;

    public RoleBasedAuthorizationFilter(JwtUtil jwtUtils, AuthorizedEndpointsConfig authorizedEndpointsConfig) {
        this.jwtUtils = jwtUtils;
        this.authorizedEndpointsConfig = authorizedEndpointsConfig;
    }

    public GatewayFilter apply() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();

            AuthorizedEndpointsConfig.EndpointConfig matchingEndpoint = findMatchingEndpoint(path);
            if (matchingEndpoint == null) {
                return handleForbidden(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return handleUnauthorized(exchange);
            }

            String token = authHeader.substring(7);
            if (!jwtUtils.validateToken(token)) {
                return handleUnauthorized(exchange);
            }

            Long userId = jwtUtils.extractId(token);
            String userRole = jwtUtils.extractRole(token);

            if (userRole == null) {
                return handleForbidden(exchange);
            }

            if (!matchingEndpoint.getRoles().contains("ANY") &&
                    !matchingEndpoint.getRoles().contains(userRole)) {
                return handleForbidden(exchange);
            }

            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .header("X-User-Role", userRole)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    private AuthorizedEndpointsConfig.EndpointConfig findMatchingEndpoint(String path) {
        return authorizedEndpointsConfig.getAuthorizedEndpoints().stream()
                .filter(endpoint -> path.startsWith(endpoint.getPath().replace("/**", "")))
                .findFirst()
                .orElse(null);
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> handleForbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}