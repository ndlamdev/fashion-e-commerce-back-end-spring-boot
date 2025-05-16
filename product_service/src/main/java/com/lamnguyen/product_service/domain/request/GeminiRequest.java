package com.lamnguyen.product_service.domain.request;

import lombok.Builder;

@Builder
public record GeminiRequest(
		Part[] contents
) {
	@Builder
	public record Part(Text[] parts) {
		@Builder
		public record Text(String text) {

		}
	}
}