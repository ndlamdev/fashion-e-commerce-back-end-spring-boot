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
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JWTGeneratorFilter extends OncePerRequestFilter {
    JwtEncoder jwtEncoder;
    ApplicationProperty applicationProperty;
    IUserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var header = JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();
        var payloadAccessToken = JWTPayload.generateAccessToken(authentication, applicationProperty.getRolePrefix());
        var user = userService.findUser(authentication.getName());
        payloadAccessToken.setUserId(user.getId());
        var accessToken = jwtEncoder.encode(JwtEncoderParameters.from(header, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .subject(authentication.getName())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), payloadAccessToken)
                .expiresAt(now.plus(applicationProperty.getExpireAccessToken(), ChronoUnit.SECONDS))
                .build()));

        var refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(header, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .subject(authentication.getName())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), JWTPayload.generateRefreshToken(authentication))
                .expiresAt(now.plus(applicationProperty.getExpireRefreshToken(), ChronoUnit.SECONDS))
                .build()));

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
