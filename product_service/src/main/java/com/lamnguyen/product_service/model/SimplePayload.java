package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.enums.JwtType;
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