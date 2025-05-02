package com.lamnguyen.base_service.config;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing; // for mogodb
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // for jpa
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EnableMongoAuditing // for mogodb
@EnableJpaAuditing // for jpa
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	@NonNull
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		return Optional.of(authentication.getName());
	}
}