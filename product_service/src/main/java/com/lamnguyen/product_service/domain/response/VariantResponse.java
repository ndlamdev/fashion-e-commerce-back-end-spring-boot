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
	Integer regularPrice;

	@JsonProperty("compare_price")
	Integer comparePrice;

	Integer quantity;

	Map<OptionType, String> options;

	boolean hide;

	Integer pending;

	@JsonProperty("product_stoppage")
	boolean productStoppage;

	@JsonProperty("product_visibility")
	boolean productVisibility;

	@JsonProperty("product_allow_buy_hidden")
	boolean productAllowBuyHidden;

	@JsonProperty("product_exclude_discount")
	boolean productExcludeDiscount;

	boolean available;

	@JsonProperty("product_apply_allowance_inventory")
	boolean productApplyAllowanceInventory;
}