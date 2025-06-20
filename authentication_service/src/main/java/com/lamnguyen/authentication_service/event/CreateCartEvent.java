package com.lamnguyen.authentication_service.event;

import lombok.Builder;

@Builder
public record CreateCartEvent(
		long userId
) {
}