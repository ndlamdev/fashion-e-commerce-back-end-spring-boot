/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:38 AM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import org.springframework.data.domain.Sort;

import java.util.List;

public record ProductFilterAndSort(
		String title,
		ProductSort sort,
		@JsonProperty("colors")
		List<String> filterColors,
		@JsonProperty("sizes")
		List<String> filterSizes
) {
	public record ProductSort(ProductTag sort, Sort.Direction direction) {

	}
}
