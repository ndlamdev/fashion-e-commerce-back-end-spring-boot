/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:36 AM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config.filter;

import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RemoveBearerTokenAuthorizationFilter extends OncePerRequestFilter {
    ApplicationProperty applicationProperty;

    public RemoveBearerTokenAuthorizationFilter(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var requestWrapper = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if (name.equals("Authorization")) return super.getHeader(name).substring("Bearer ".length());
                return super.getHeader(name);
            }
        };
        filterChain.doFilter(requestWrapper, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return !applicationProperty.getWhiteList().contains(request.getServletPath());
    }
}
