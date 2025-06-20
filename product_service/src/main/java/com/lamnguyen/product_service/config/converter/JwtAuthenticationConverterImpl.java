/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:55 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.product_service.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.product_service.model.JWTPayload;
import com.lamnguyen.product_service.utils.property.ApplicationProperty;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JwtAuthenticationConverterImpl implements Converter<Jwt, AbstractAuthenticationToken> {
	ApplicationProperty applicationProperty;

	@Override
	@Transactional
	public AbstractAuthenticationToken convert(@NonNull Jwt source) {
		var payload = new ObjectMapper().convertValue(source.getClaimAsMap(applicationProperty.getJwtClaim()), JWTPayload.class);
		Set<GrantedAuthority> authorities = new HashSet<>();
		if (payload.getRoles().contains("ROLE_ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities.addAll(payload.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
		}
		return new JwtAuthenticationToken(source, authorities);
	}
}
