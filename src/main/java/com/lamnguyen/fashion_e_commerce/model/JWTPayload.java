package com.lamnguyen.fashion_e_commerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lamnguyen.fashion_e_commerce.util.enums.JWT_TYPE;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTPayload{
    long userId;
    String email;
    List<String> role;
    List<String> scope;
    JWT_TYPE type;

    public JWTPayload(long userId, String email, List<String> role, List<String> scope, JWT_TYPE type) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.scope = scope;
        if (type == null) type = JWT_TYPE.ACCESS_TOKEN;
        this.type = type;
    }

    public static JWTPayload generateAccessToken(Authentication authentication, String prefixRole) {
        List<String> role = new ArrayList<>();
        List<String> scope = new ArrayList<>();
        for (var authority : authentication.getAuthorities()) {
            if (authority.getAuthority().startsWith(prefixRole))
                role.add(authority.getAuthority().substring(5));
            else scope.add(authority.getAuthority().substring(6));
        }

        return JWTPayload.builder()
                .email(authentication.getName())
                .role(role)
                .scope(scope)
                .build();
    }

    public static JWTPayload generateRefreshToken(Authentication authentication) {
        return JWTPayload.builder()
                .email(authentication.getName())
                .type(JWT_TYPE.REFRESH_TOKEN)
                .build();
    }
}