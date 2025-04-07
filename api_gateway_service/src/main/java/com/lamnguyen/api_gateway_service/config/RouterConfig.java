/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:44 PM - 04/04/2025
 * User: kimin
 **/

package com.lamnguyen.api_gateway_service.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouterConfig {
	@Value("${server.servlet.context-path}")
	String contextPath;

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("authentication-service", pre -> pre.path(contextPath + "/auth/**")
						.filters(gatewayFilterSpec -> gatewayFilterSpec.stripPrefix(2))
						.uri("lb://FASHION-E-COMMERCE-AUTHENTICATION-SERVICE"))
				.build();
	}
}
