/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.profile_service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lamnguyen.profile_service.model.JWTPayload;
import com.lamnguyen.profile_service.model.SimplePayload;
import com.lamnguyen.profile_service.utils.enums.JwtType;
import com.lamnguyen.profile_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class JwtTokenUtil {
    ApplicationProperty applicationProperty;

    public DecodedJWT decodeTokenNotVerify(String token) {
        return JWT.decode(token);
    }

    public JWTPayload getPayloadNotVerify(DecodedJWT token) {
        return getPayload(token.getClaim(applicationProperty.getJwtClaim()).asMap());
    }

    public SimplePayload getSimplePayloadNotVerify(DecodedJWT token) {
        return getPayload(token.getClaim(applicationProperty.getJwtClaim()).asMap());
    }

    private JWTPayload getPayload(Map<String, Object> data) {
        return JWTPayload.builder()
                .userId(((Number) data.getOrDefault("userId", 0L)).longValue())
                .email((String) data.getOrDefault("email", null))
                .refreshTokenId((String) data.getOrDefault("refreshTokenId", null))
                .type(JwtType.getEnum(data.getOrDefault("type", null)))
                .roles(new HashSet<>((ArrayList<String>) data.getOrDefault("roles", new ArrayList<String>())))
                .build();
    }

}
