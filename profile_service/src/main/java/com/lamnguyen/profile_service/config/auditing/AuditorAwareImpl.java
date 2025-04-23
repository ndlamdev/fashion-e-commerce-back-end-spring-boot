package com.lamnguyen.profile_service.config.auditing;

import com.lamnguyen.profile_service.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
@AllArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    JwtTokenUtil jwtTokenUtil;

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        var authContext = SecurityContextHolder.getContext().getAuthentication();
        if (authContext == null) return Optional.empty();
        if (authContext instanceof AnonymousAuthenticationToken anonymous) {
            return Optional.of(anonymous.getName());
        }

        var authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var token = authentication.getToken().getTokenValue();
        var jwtPayload = jwtTokenUtil.getPayloadNotVerify(jwtTokenUtil.decodeTokenNotVerify(token));
        var email = jwtPayload.getEmail();

        return Optional.ofNullable(email);
    }
}
