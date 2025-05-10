package com.example.passerelle_api.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product_route", r -> r
                        .path("/api/products/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("order_route", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://COMMANDE-SERVICE"))
                .build();
    }
}
