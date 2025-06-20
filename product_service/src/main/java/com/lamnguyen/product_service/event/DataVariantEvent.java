/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.product_service.event;

import com.lamnguyen.product_service.utils.enums.OptionType;

import java.util.List;

public record DataVariantEvent(
		String id,
		double comparePrice,
		double regularPrice,
		List<Option> options
) {
	public record Option(OptionType type, List<String> values) {
	}
}
