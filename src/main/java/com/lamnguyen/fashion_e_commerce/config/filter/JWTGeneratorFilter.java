/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:29 PM - 01/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config.filter;

import com.lamnguyen.fashion_e_commerce.model.JWTPayload;
import com.lamnguyen.fashion_e_commerce.service.business.user.IUserService;
import com.lamnguyen.fashion_e_commerce.util.JwtTokenUtil;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JWTGeneratorFilter extends OncePerRequestFilter {
    ApplicationProperty applicationProperty;
    IUserService userService;
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var user = userService.findUserByEmail(authentication.getName());
        var refreshToken = jwtTokenUtil.generateRefreshToken(user);

        var payloadAccessToken = JWTPayload.generateForAccessToken(authentication);
        payloadAccessToken.setUserId(user.getId());
        payloadAccessToken.setRefreshTokenId(refreshToken.getId());
        var accessToken = jwtTokenUtil.generateAccessToken(user, payloadAccessToken);

        var session = request.getSession();
        session.setAttribute(applicationProperty.getKeyAccessToken(), accessToken.getTokenValue());
        session.setAttribute(applicationProperty.getKeyRefreshToken(), refreshToken.getTokenValue());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return !applicationProperty.getLoginPath().equals(request.getServletPath());
    }
}
