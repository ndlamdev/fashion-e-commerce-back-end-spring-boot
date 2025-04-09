/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:41 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.request;

import jakarta.validation.constraints.NotBlank;

public record TitleCollectionRequest(
		@NotBlank
		String title
) {
}
