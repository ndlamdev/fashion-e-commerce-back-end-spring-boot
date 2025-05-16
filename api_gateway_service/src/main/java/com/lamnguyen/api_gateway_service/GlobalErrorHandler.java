package com.lamnguyen.api_gateway_service;

import io.netty.channel.ConnectTimeoutException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2) // Đảm bảo handler này ưu tiên hơn mặc định
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {

	public GlobalErrorHandler(ErrorAttributes errorAttributes,
	                          ApplicationContext applicationContext,
	                          ServerCodecConfigurer serverCodecConfigurer) {
		super(errorAttributes, new WebProperties.Resources(), applicationContext);
		this.setMessageWriters(serverCodecConfigurer.getWriters());
		this.setMessageReaders(serverCodecConfigurer.getReaders());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

		Throwable error = getError(request);
		String message = error.getMessage();

		Object status = HttpStatus.INTERNAL_SERVER_ERROR;
		var code = HttpStatus.INTERNAL_SERVER_ERROR.value();
		if (error instanceof ConnectTimeoutException || error instanceof java.util.concurrent.TimeoutException) {
			status = "OVERLOAD";
			message = "The system is overloaded.";
		}

		Map<String, Object> responseBody = Map.of(
				"code", code,
				"error", status,
				"detail", message
		);

		return ServerResponse
				.status(code)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(responseBody);
	}
}
