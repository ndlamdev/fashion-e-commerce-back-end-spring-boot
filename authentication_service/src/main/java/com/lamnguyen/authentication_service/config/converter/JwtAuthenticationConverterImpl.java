/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:55 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.authentication_service.model.JWTPayload;
import com.lamnguyen.authentication_service.service.authorization.IRoleService;
import com.lamnguyen.authentication_service.util.property.ApplicationProperty;
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
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JwtAuthenticationConverterImpl implements Converter<Jwt, AbstractAuthenticationToken> {
	ApplicationProperty applicationProperty;
	IRoleService iRoleService;

	@Override
	@Transactional
	public AbstractAuthenticationToken convert(@NonNull Jwt source) {
		var payload = new ObjectMapper().convertValue(source.getClaimAsMap(applicationProperty.getJwtClaim()), JWTPayload.class);
		System.out.println(payload);
		Set<GrantedAuthority> authorities = new HashSet<>();
		if (payload.getRoles().contains("ROLE_ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			payload.getRoles().forEach(it -> {
				if (!it.startsWith(applicationProperty.getRolePrefix())) return;
				var roles = iRoleService.getByName(it.substring(applicationProperty.getRolePrefix().length()));
				authorities.addAll(roles.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission().getName())).collect(Collectors.toSet()));
			});
		}
		return new JwtAuthenticationToken(source, authorities);
	}
}
