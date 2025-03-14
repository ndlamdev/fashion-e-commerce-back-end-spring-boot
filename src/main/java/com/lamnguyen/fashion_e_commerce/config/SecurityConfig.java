/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:08 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config;

import com.lamnguyen.fashion_e_commerce.config.converter.JwtAuthenticationConverterImpl;
import com.lamnguyen.fashion_e_commerce.config.filter.AuthorizationFilterImpl;
import com.lamnguyen.fashion_e_commerce.config.filter.CheckBlacklistTokenFilter;
import com.lamnguyen.fashion_e_commerce.config.filter.JWTGeneratorFilter;
import com.lamnguyen.fashion_e_commerce.config.filter.UsernamePasswordJsonAuthenticationFilter;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableCaching
public class SecurityConfig {
    ApplicationProperty applicationProperty;
    CorsConfigurationSourceImpl corsConfigurationSource;
    AuthorizationFilterImpl authorizationFilter;
    JwtAuthenticationConverterImpl jwtAuthenticationConverter;
    JWTGeneratorFilter jwtGeneratorFilter;
    AuthenticationManager manager;
    CheckBlacklistTokenFilter checkBlacklistTokenFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterAt(new UsernamePasswordJsonAuthenticationFilter(applicationProperty.getLoginPath(), manager), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(jwtGeneratorFilter, UsernamePasswordJsonAuthenticationFilter.class);
        httpSecurity.addFilterAfter(checkBlacklistTokenFilter, BearerTokenAuthenticationFilter.class);
        httpSecurity.addFilterBefore(authorizationFilter, AuthorizationFilter.class);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(corsConfig -> corsConfig
                .configurationSource(corsConfigurationSource)
        );
        httpSecurity.authorizeHttpRequests(authorization -> authorization
                .requestMatchers("/**").permitAll()
        );
        httpSecurity.oauth2ResourceServer(oauth2ResourceServerConfig -> oauth2ResourceServerConfig
                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter))
        );
        httpSecurity.sessionManagement(sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
