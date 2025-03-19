/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.fashion_e_commerce.model.JWTPayload;
import com.lamnguyen.fashion_e_commerce.model.SimplePayload;
import com.lamnguyen.fashion_e_commerce.model.User;
import com.lamnguyen.fashion_e_commerce.util.enums.JwtType;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class JwtTokenUtil {
    JwtDecoder jwtDecoder;
    JwtEncoder jwtEncoder;
    JwsHeader jwsHeader;
    ApplicationProperty applicationProperty;

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    public JWTPayload getPayload(Jwt token) {
        return new ObjectMapper().convertValue(token.getClaim(applicationProperty.getJwtClaim()), JWTPayload.class);
    }

    public SimplePayload getSimplePayload(Jwt token) {
        return new ObjectMapper().convertValue(token.getClaim(applicationProperty.getJwtClaim()), SimplePayload.class);
    }

    public Jwt generateRefreshToken(User user) {
        return generateTokenSimplePayLoad(user, JwtType.REFRESH_TOKEN);
    }

    private Jwt generateTokenSimplePayLoad(User user, JwtType jwtType) {
        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .subject(user.getEmail())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), SimplePayload.builder()
                        .type(jwtType)
                        .userId(user.getId())
                        .email(user.getEmail())
                        .build())
                .expiresAt(now.plus(applicationProperty.getExpireRefreshToken(), ChronoUnit.SECONDS))
                .build()));
    }

    public Jwt generateAccessToken(User user, JWTPayload payload) {
        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .subject(user.getEmail())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), payload)
                .expiresAt(now.plus(applicationProperty.getExpireAccessToken(), ChronoUnit.SECONDS))
                .build()));
    }

    public Jwt generateTokenResetPassword(User user) {
        return generateTokenSimplePayLoad(user, JwtType.RESET_PASSWORD);
    }
}
