/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:55 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.fashion_e_commerce.model.JWTPayload;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JwtAuthenticationConverterImpl implements Converter<Jwt, AbstractAuthenticationToken> {
    ApplicationProperty applicationProperty;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        var payload = new ObjectMapper().convertValue(source.getClaimAsMap(applicationProperty.getJwtClaim()), JWTPayload.class);
        List<GrantedAuthority> authorities = new ArrayList<>(payload.getRole().stream().map(it -> new SimpleGrantedAuthority(applicationProperty.getRolePrefix() + it)).toList());
        authorities.addAll(payload.getScope().stream().map(it -> new SimpleGrantedAuthority(applicationProperty.getPermissionPrefix() + it)).toList());
        return new JwtAuthenticationToken(source, authorities);
    }
}
