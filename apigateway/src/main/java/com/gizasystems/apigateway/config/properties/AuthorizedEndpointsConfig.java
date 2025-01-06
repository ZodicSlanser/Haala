package com.gizasystems.apigateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "gateway")
public class AuthorizedEndpointsConfig {

    private List<EndpointConfig> authorizedEndpoints;

    public List<EndpointConfig> getAuthorizedEndpoints() {
        return authorizedEndpoints;
    }

    public void setAuthorizedEndpoints(List<EndpointConfig> authorizedEndpoints) {
        this.authorizedEndpoints = authorizedEndpoints;
    }

    public static class EndpointConfig {
        private String path;
        private List<String> roles;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}
