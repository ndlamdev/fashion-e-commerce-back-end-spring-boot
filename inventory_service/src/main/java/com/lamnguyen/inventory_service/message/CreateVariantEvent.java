/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.message;

import com.lamnguyen.inventory_service.utils.enums.OptionType;

import java.util.List;

public record CreateVariantEvent(
	String id,
	List<Option> options
) {
	public record Option(OptionType type, List<String> values) {
	}
}
