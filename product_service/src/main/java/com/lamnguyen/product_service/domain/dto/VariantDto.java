package com.lamnguyen.product_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.product_service.model.MongoBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class VariantDto extends MongoBaseEntity {
	@JsonProperty("product_id")
	String productId;

	String title;

	@JsonProperty("regular_price")
	Integer regularPrice;

	@JsonProperty("compare_price")
	Integer comparePrice;

	Integer quantity;

	@JsonProperty("option_id_1")
	String option1;
	@JsonProperty("option_id_2")
	String option2;
	@JsonProperty("option_id_3")
	String option3;
	@JsonProperty("option_id_4")
	String option4;

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

	@JsonProperty("display_collections_variant")
	Integer displayCollectionsVariant;
}