/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:42 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.request;

import com.lamnguyen.product_service.utils.enums.OptionType;

import java.util.List;

public record CreateImageOptionsValueRequest(
		String title,

		OptionType option,

		List<CreateOptionItemRequest> options
) {
}
