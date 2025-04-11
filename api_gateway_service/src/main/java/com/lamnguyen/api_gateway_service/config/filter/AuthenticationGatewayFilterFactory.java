/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:35 PM - 10/04/2025
 * User: kimin
 **/

package com.lamnguyen.api_gateway_service.config.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
	WebClient.Builder client;

	public AuthenticationGatewayFilterFactory(WebClient.Builder builder) {
		super(Config.class);
		this.client = builder;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			System.out.println("Authentication Filter: " + config.getBaseMessage());

			String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

			if (token == null || !token.startsWith("Bearer ")) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return onError(exchange, "Unauthorized", HttpStatus.UNAUTHORIZED);
			}

			// ✅ Chỉ return duy nhất đoạn xử lý WebClient + chain
			return client
					.baseUrl("lb://FASHION-E-COMMERCE-AUTHENTICATION-SERVICE")
					.build()
					.get()
					.uri("/api/auth/v1/validate") // endpoint xác thực token
					.header(HttpHeaders.AUTHORIZATION, token)
					.exchangeToMono(clientResponse -> {
						if (clientResponse.statusCode().is2xxSuccessful()) {
							// Lấy token mới từ response header
							String newToken = clientResponse.headers()
									.header(HttpHeaders.AUTHORIZATION)
									.stream()
									.findFirst()
									.orElse(token); // fallback nếu không có header

							// Gắn header mới vào exchange request
							ServerHttpRequest mutatedRequest = exchange.getRequest()
									.mutate()
									.header(HttpHeaders.AUTHORIZATION, "Bearer " + newToken)
									.build();

							ServerWebExchange mutatedExchange = exchange.mutate()
									.request(mutatedRequest)
									.build();

							return chain.filter(mutatedExchange)
									.doOnSuccess(done -> {
										if (config.isPostAuth()) {
											System.out.println("✅ PostAuth done.");
										}
									});
						} else {
							return clientResponse.bodyToMono(String.class)
									.flatMap(body -> {
										System.out.println("❌ Token invalid: " + body);
										return onError(exchange, "Unauthorized", HttpStatus.UNAUTHORIZED);
									});
						}
					})
					.onErrorResume(err -> {
						System.out.println("❌ Authentication Error: " + err.getMessage());
						return errorHandle(exchange, err);
					});
		};
	}

	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class Config {
		String baseMessage;
		boolean preAuth;
		boolean postAuth;
	}

	private Mono<Void> errorHandle(ServerWebExchange exchange, Throwable ex) {
		if (ex instanceof WebClientResponseException webClientResponseException) {
			// Nếu bạn muốn giữ nguyên body JSON trả về từ authentication-service:
			String body = webClientResponseException.getResponseBodyAsString(); // JSON gốc

			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

			DataBuffer buffer = exchange.getResponse().bufferFactory()
					.wrap(body.getBytes(StandardCharsets.UTF_8));

			return exchange.getResponse().writeWith(Mono.just(buffer));
		}

		// Fallback nếu lỗi không phải từ WebClient (ví dụ timeout)
		return onError(exchange, "Internal authentication error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		String errorJson = String.format("{\"error\": \"%s\"}", message);
		byte[] bytes = errorJson.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return exchange.getResponse().writeWith(Mono.just(buffer));
	}
}

