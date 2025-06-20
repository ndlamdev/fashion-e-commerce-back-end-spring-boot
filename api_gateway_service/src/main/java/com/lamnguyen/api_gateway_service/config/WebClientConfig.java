/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:58 PM - 10/04/2025
 * User: kimin
 **/

package com.lamnguyen.api_gateway_service.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration("WebClientConfig")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebClientConfig {
	HttpClient httpClient;

	@Bean
	@LoadBalanced
	public WebClient.Builder webClient() {
		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient));
	}
}
