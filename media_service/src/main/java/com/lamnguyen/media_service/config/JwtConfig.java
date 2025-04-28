/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:36 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.media_service.config;

import com.lamnguyen.media_service.utils.property.ApplicationProperty;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JwtConfig {
	ApplicationProperty applicationProperty;

	@Bean
	public JwsHeader jwsHeader() {
		return JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(applicationProperty.getSecretKey().getBytes(), MacAlgorithm.HS256.getName())).build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(new SecretKeySpec(applicationProperty.getSecretKey().getBytes(), MacAlgorithm.HS256.getName())));
	}
}
