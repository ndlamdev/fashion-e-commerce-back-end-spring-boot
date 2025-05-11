/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:45 AM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.config;

import com.lamnguyen.cart_service.config.converter.JwtAuthenticationConverterImpl;
import com.lamnguyen.cart_service.config.endpoint.CustomAuthenticationEntryPoint;
import com.lamnguyen.cart_service.utils.property.ApplicationProperty;
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
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableCaching
@EnableAsync
@EnableMethodSecurity
public class SecurityConfig {
	JwtAuthenticationConverterImpl jwtAuthenticationConverter;
	CustomAuthenticationEntryPoint authenticationEntryPoint;
	CorsConfigurationSourceImpl configurationSource;
	ApplicationProperty applicationProperty;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(auth -> auth
				.requestMatchers("/actuator/**").permitAll()
				.requestMatchers(applicationProperty.getWhiteList().toArray(String[]::new)).permitAll()
				.anyRequest().permitAll()
		);
		httpSecurity.oauth2ResourceServer(oauth2ResourceServerConfig -> oauth2ResourceServerConfig
				.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))
				.authenticationEntryPoint(authenticationEntryPoint)
		);
		httpSecurity.cors(con -> con.configurationSource(configurationSource));
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		httpSecurity.exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint));
		return httpSecurity.build();
	}
}
