package com.lamnguyen.authentication_service.config;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientInterceptorConfig {

	@GrpcGlobalClientInterceptor
	@Bean
	public OpenTelemetryClientInterceptor openTelemetryClientInterceptor() {
		return new OpenTelemetryClientInterceptor();
	}
}