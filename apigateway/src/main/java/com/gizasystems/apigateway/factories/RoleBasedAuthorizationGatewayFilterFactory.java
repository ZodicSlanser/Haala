package com.gizasystems.apigateway.factories;

import com.gizasystems.apigateway.config.RoleBasedAuthorizationFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class RoleBasedAuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<RoleBasedAuthorizationGatewayFilterFactory.Config> {
    private final RoleBasedAuthorizationFilter filter;

    public RoleBasedAuthorizationGatewayFilterFactory(RoleBasedAuthorizationFilter filter) {
        super(Config.class);
        this.filter = filter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return filter.apply();
    }

    public static class Config {
    }
}