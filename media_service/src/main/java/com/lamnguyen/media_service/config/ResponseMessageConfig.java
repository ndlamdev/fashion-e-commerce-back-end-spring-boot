/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:33 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.media_service.config;

import com.lamnguyen.media_service.domain.ApiSuccessResponse;
import com.lamnguyen.media_service.utils.annotation.ApiMessageResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseMessageConfig implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        var req = ((ServletServerHttpRequest) request).getServletRequest();
        if (req.getProtocol().equalsIgnoreCase("http") || req.getProtocol().equalsIgnoreCase("https")) return body;
        var res = ((ServletServerHttpResponse) response).getServletResponse();
        if (res.getStatus() >= 400) return body;
        var apiMessage = returnType.getMethodAnnotation(ApiMessageResponse.class);
        String message = "No message!";
        if (apiMessage != null) message = apiMessage.value();
        return ApiSuccessResponse.builder()
                .message(message)
                .data(body)
                .code(res.getStatus())
                .build();
    }
}
