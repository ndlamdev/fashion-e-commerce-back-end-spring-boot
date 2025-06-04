/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:26 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.domain.ApiResponseError;
import com.lamnguyen.authentication_service.domain.request.UsernamePasswordAuthenticationRequest;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class UsernamePasswordJsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public UsernamePasswordJsonAuthenticationFilter(String url, AuthenticationManager manager) {
        setFilterProcessesUrl(url);
        setAuthenticationManager(manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            UsernamePasswordAuthenticationRequest data = null;
            try {
                data = new Gson().fromJson(request.getReader(), UsernamePasswordAuthenticationRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (data == null) throw new AuthenticationServiceException("Please submit username and password!");
            String username = data.getEmail() == null ? "" : data.getEmail();
            String password = data.getPassword() == null ? "" : data.getPassword();
            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();
        this.logger.trace("Failed to process authentication request", failed);
        this.logger.trace("Cleared SecurityContextHolder");
        this.logger.trace("Handling authentication failure");
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var writer = response.getWriter();
        var message  = switch (failed) {
            case DisabledException e -> "Vui lòng xác thực tài khoản!";
            case BadCredentialsException e -> "Mật khẩu không chính xác!";
            case InternalAuthenticationServiceException e -> "Tài khoản không tồn tại!";
            default -> "Lỗi!";
        };
        new ObjectMapper().writeValue(writer, ApiResponseError.builder()
                .code(ExceptionEnum.CODE_NOT_FOUND.getCode())
                .error(ExceptionEnum.LOGIN_FAIL.name())
                .detail(message)
                .trace(failed.getStackTrace())
                .build());
    }
}
