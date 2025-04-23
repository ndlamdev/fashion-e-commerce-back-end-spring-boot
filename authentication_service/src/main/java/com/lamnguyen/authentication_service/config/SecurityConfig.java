/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:08 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.lamnguyen.authentication_service.config.converter.JwtAuthenticationConverterImpl;
import com.lamnguyen.authentication_service.config.endpoint.CustomAuthenticationEntryPoint;
import com.lamnguyen.authentication_service.config.filter.CheckBlacklistTokenFilter;
import com.lamnguyen.authentication_service.config.filter.JWTGeneratorFilter;
import com.lamnguyen.authentication_service.config.filter.RemoveBearerTokenAuthorizationFilter;
import com.lamnguyen.authentication_service.config.filter.UsernamePasswordJsonAuthenticationFilter;
import com.lamnguyen.authentication_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableCaching
@EnableAsync
@EnableMethodSecurity
public class SecurityConfig {
	ApplicationProperty applicationProperty;
	CorsConfigurationSourceImpl corsConfigurationSource;
	JwtAuthenticationConverterImpl jwtAuthenticationConverter;
	JWTGeneratorFilter jwtGeneratorFilter;
	AuthenticationManager manager;
	CheckBlacklistTokenFilter checkBlacklistTokenFilter;
	CustomAuthenticationEntryPoint authenticationEntryPoint;
	RemoveBearerTokenAuthorizationFilter removeBearerTokenAuthorizationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.addFilterAt(new UsernamePasswordJsonAuthenticationFilter(applicationProperty.getLoginPath(), manager), UsernamePasswordAuthenticationFilter.class);
		httpSecurity.addFilterAfter(jwtGeneratorFilter, UsernamePasswordJsonAuthenticationFilter.class);
		httpSecurity.addFilterBefore(removeBearerTokenAuthorizationFilter, BearerTokenAuthenticationFilter.class);
		httpSecurity.addFilterAfter(checkBlacklistTokenFilter, BearerTokenAuthenticationFilter.class);
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

	@Bean
	public GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest() {
		return new GoogleAuthorizationCodeTokenRequest(
				new NetHttpTransport(),
				new GsonFactory(),
				"https://oauth2.googleapis.com/token",
				applicationProperty.getClientId(),
				applicationProperty.getClientSecret(),
				"",
				"postmessage" // or your redirect URI
		);
	}

	@Bean
	public GoogleIdTokenVerifier googleIdTokenVerifier() {
		return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
				.setAudience(Collections.singletonList(applicationProperty.getClientId()))
				.build();
	}
}
