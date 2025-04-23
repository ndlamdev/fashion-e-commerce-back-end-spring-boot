package com.lamnguyen.profile_service.config.auditing;

import com.lamnguyen.profile_service.utils.JwtTokenUtil;
import com.lamnguyen.profile_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Properties;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class PersistentConfig {
    ApplicationProperty applicationProperty;
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl(new JwtTokenUtil(applicationProperty));
    }
}
