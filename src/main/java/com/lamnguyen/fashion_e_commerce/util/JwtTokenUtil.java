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
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class JwtTokenUtil {
    JwtDecoder jwtDecoder;
    ApplicationProperty applicationProperty;

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    public JWTPayload getPayload(Jwt token) {
        return new ObjectMapper().convertValue(token.getClaim(applicationProperty.getJwtClaim()), JWTPayload.class);
    }
}
