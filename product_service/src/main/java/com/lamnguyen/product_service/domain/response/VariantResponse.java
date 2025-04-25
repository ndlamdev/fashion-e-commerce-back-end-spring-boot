package com.lamnguyen.product_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.product_service.model.MongoBaseDocument;
import com.lamnguyen.product_service.utils.enums.OptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class VariantResponse extends MongoBaseDocument implements Serializable {
	@JsonProperty("product_id")
	String productId;

	String title;

	@JsonProperty("regular_price")
	double regularPrice;

	@JsonProperty("compare_price")
	double comparePrice;

	int quantity;

	Map<OptionType, String> options;

	@JsonProperty("product_visibility")
	boolean productVisibility;

	@JsonProperty("product_allow_buy_when_hidden")
	boolean productAllowBuyWhenClocked;

	@JsonProperty("product_exclude_discount")
	boolean productExcludeDiscount;

	@JsonProperty("product_apply_allowance_inventory")
	boolean productApplyAllowanceInventory;

	int pending;

	boolean delete;
}