/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:38 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config;

import com.lamnguyen.authentication_service.service.authentication.v1.UserDetailServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsernamePasswordAuthenticationConfig {
    UserDetailServiceImpl userDetailService;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(new DaoAuthenticationProvider() {{
            setPasswordEncoder(passwordEncoder());
            setUserDetailsService(userDetailService);
        }});
    }
}
