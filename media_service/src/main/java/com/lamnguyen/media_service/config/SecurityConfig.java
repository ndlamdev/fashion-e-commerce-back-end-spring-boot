/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:08 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.media_service.config;

import com.lamnguyen.media_service.config.converter.JwtAuthenticationConverterImpl;
import com.lamnguyen.media_service.config.endpoint.CustomAuthenticationEntryPoint;
import com.lamnguyen.media_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableCaching
@EnableAsync
@EnableMethodSecurity
public class SecurityConfig {
	ApplicationProperty applicationProperty;
	CorsConfigurationSourceImpl corsConfigurationSource;
	JwtAuthenticationConverterImpl jwtAuthenticationConverter;
	CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		httpSecurity.cors(corsConfig -> corsConfig
				.configurationSource(corsConfigurationSource)
		);
		httpSecurity.authorizeHttpRequests(authorization -> authorization
				.requestMatchers(applicationProperty.getWhiteList().toArray(String[]::new)).permitAll()
				.requestMatchers("/**").authenticated()
		);
		httpSecurity.oauth2ResourceServer(oauth2ResourceServerConfig -> oauth2ResourceServerConfig
				.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))
				.authenticationEntryPoint(authenticationEntryPoint)
		);
		httpSecurity.exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint));
		httpSecurity.sessionManagement(sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return httpSecurity.build();
	}
}
