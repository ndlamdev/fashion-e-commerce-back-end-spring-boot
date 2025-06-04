/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:11 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.media_service.config.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.media_service.config.exception.ExceptionEnum;
import com.lamnguyen.media_service.domain.ApiResponseError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component("authenticationEntryPoint")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
	ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		this.delegate.commence(request, response, authException);
		response.setContentType("application/json;charset=UTF-8");

		String errorMessage = Optional.ofNullable(authException.getCause())
				.map(Throwable::getMessage)
				.orElse(authException.getMessage());
		ApiResponseError<Object> res = ApiResponseError.builder()
				.code(ExceptionEnum.UNAUTHORIZED.getCode())
				.error(ExceptionEnum.UNAUTHORIZED.name())
				.detail(errorMessage)
				.build();
		objectMapper.writeValue(response.getWriter(), res);
	}
}


