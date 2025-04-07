package com.lamnguyen.authentication_service.model;

import com.lamnguyen.authentication_service.util.enums.JwtType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@RequiredArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimplePayload {
    long userId;
    @Builder.Default
    JwtType type = JwtType.ACCESS_TOKEN;
    String email;
}