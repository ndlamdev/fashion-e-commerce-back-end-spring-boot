package com.lamnguyen.fashion_e_commerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lamnguyen.fashion_e_commerce.util.enums.JwtType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class JWTPayload extends SimplePayload{
    String refreshTokenId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Set<String> roles;

    public static JWTPayload generateForAccessToken(Authentication authentication) {
        return JWTPayload.builder()
                .email(authentication.getName())
                .type(JwtType.ACCESS_TOKEN)
                .roles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    public static JWTPayload generateForAccessToken(User user,String refreshTokenId) {
        return JWTPayload.builder()
                .userId(user.getId())
                .refreshTokenId(refreshTokenId)
                .email(user.getEmail())
                .type(JwtType.ACCESS_TOKEN)
                .roles(user.getRoles().stream().map(it -> "ROLE_" + it.getRole().getName()).collect(Collectors.toSet()))
                .build();
    }
}