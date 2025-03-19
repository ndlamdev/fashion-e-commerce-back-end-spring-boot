package com.lamnguyen.fashion_e_commerce.model;

import com.lamnguyen.fashion_e_commerce.util.enums.JwtType;
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