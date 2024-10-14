package com.nhnacademy.router1.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {
    @Bean
    public RouteLocator myRoute(RouteLocatorBuilder builder) {

        RouteLocator routeLocator =  builder.routes().build();
        return builder.routes()
                .route("nhn-academy-mini-dooray-account",
                        p -> p.path("/").uri("lb://nhn-academy-mini-dooray-account"))
                .route("nhn-academy-mini-dooray-account",
                        p -> p.path("/auth/**").uri("lb://nhn-academy-mini-dooray-account"))
                .route("nhn-academy-mini-dooray-task",
                        p -> p.path("/task/**").uri("lb://nhn-academy-mini-dooray-task"))
                .route("nhn-academy-mini-dooray-task",
                        p -> p.path("/project/**").uri("lb://nhn-academy-mini-dooray-task"))
                .build();
    }

}
