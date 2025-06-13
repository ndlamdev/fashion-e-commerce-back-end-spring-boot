package com.lamnguyen.product_service.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VariantResponse implements Serializable {
	String id;

	String productId;

	String title;

	double regularPrice;

	double comparePrice;

	int quantity;

	Map<OptionType, String> options;

	boolean productVisibility;

	boolean productAllowBuyWhenClocked;

	boolean productExcludeDiscount;

	boolean productApplyAllowanceInventory;

	int pending;

	boolean delete;
}