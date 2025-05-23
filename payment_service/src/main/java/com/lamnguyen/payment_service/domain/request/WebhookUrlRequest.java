/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:45 PM-22/05/2025
 * User: kimin
 **/
package com.lamnguyen.payment_service.domain.request;

import jakarta.validation.constraints.NotBlank;

public record WebhookUrlRequest(
		@NotBlank
		String url
) {
}
