/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.utils.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.authentication_service.domain.dto.FacebookPayloadDto;
import com.lamnguyen.authentication_service.domain.dto.GooglePayloadDto;
import com.lamnguyen.authentication_service.model.JWTPayload;
import com.lamnguyen.authentication_service.model.SimplePayload;
import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.utils.enums.JwtType;
import com.lamnguyen.authentication_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class JwtTokenUtil {
    JwtDecoder jwtDecoder;
    JwtEncoder jwtEncoder;
    JwsHeader jwsHeader;
    ApplicationProperty applicationProperty;
    ObjectMapper objectMapper;

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    public JWTPayload getPayload(Jwt token) {
        return getPayload(token.<Map<String, Object>>getClaim(applicationProperty.getJwtClaim()));
    }

    public SimplePayload getSimplePayload(Jwt token) {
        return getPayload(token.<Map<String, Object>>getClaim(applicationProperty.getJwtClaim()));
    }

    public DecodedJWT decodeTokenNotVerify(String token) {
        return JWT.decode(token);
    }

    public JWTPayload getPayloadNotVerify(DecodedJWT token) {
        return getPayload(token.getClaim(applicationProperty.getJwtClaim()).asMap());
    }

    public SimplePayload getSimplePayloadNotVerify(DecodedJWT token) {
        return getPayload(token.getClaim(applicationProperty.getJwtClaim()).asMap());
    }

    public Jwt generateRefreshToken(User user) {
        return generateTokenSimplePayLoad(user, JwtType.REFRESH_TOKEN);
    }

    private JWTPayload getPayload(Map<String, Object> data) {
        return objectMapper.convertValue(data, JWTPayload.class);
//        return JWTPayload.builder()
//                .userId(((Number) data.getOrDefault("user_id", 0L)).longValue())
//                .email((String) data.getOrDefault("email", null))
//                .refreshTokenId((String) data.getOrDefault("refresh_token_id", null))
//                .type(JwtType.getEnum(data.getOrDefault("type", null)))
//                .roles(new HashSet<>((ArrayList<String>) data.getOrDefault("roles", new ArrayList<String>())))
//                .build();
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

    public Jwt generateAccessToken(JWTPayload payload) {
        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .subject(payload.getEmail())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), payload)
                .expiresAt(now.plus(applicationProperty.getExpireAccessToken(), ChronoUnit.SECONDS))
                .build()));
    }

    public Jwt generateTokenResetPassword(User user) {
        return generateTokenSimplePayLoad(user, JwtType.RESET_PASSWORD);
    }

    public Jwt generateRegisterWithGoogleToken(GooglePayloadDto payload) {
        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .subject(payload.email())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), payload)
                .expiresAt(now.plus(applicationProperty.getExpireRegisterToken(), ChronoUnit.MINUTES))
                .build()));
    }

    public GooglePayloadDto getGooglePayloadDtoNotVerify(String token) {
        return objectMapper.convertValue(JWT.decode(token).getClaim(applicationProperty.getJwtClaim()), GooglePayloadDto.class);
    }

    public Jwt generateRegisterToken(FacebookPayloadDto facebookPayloadDto) {
        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(applicationProperty.getJwtIss())
                .issuedAt(now)
                .claim(applicationProperty.getJwtClaim(), facebookPayloadDto)
                .expiresAt(now.plus(applicationProperty.getExpireRegisterToken(), ChronoUnit.MINUTES))
                .build()));
    }

    public FacebookPayloadDto getFacebookPayloadDtoNotVerify(String token) {
        return objectMapper.convertValue(JWT.decode(token).getClaim(applicationProperty.getJwtClaim()), FacebookPayloadDto.class);
    }
}
