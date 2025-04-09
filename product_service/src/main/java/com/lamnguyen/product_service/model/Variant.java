package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Variant extends MongoBaseEntity {
	@DBRef
	Product product;

	String title;

	@Field("regular_price")
	Integer regularPrice;

	@Field("compare_price")
	Integer comparePrice;

	Integer quantity;

	String option1;
	String option2;
	String option3;
	String option4;

	@Field("sync_product")
	List<SyncProduct> syncProduct;

	boolean hide;
	Integer pending;

	@Field("based_product_id")
	String basedProductId;

	@Field("product_stoppage")
	boolean productStoppage;

	@Field("product_visibility")
	boolean productVisibility;

	@Field("product_allow_buy_hidden")
	boolean productAllowBuyHidden;

	@Field("product_exclude_discount")
	boolean productExcludeDiscount;

	boolean available;

	@Field("product_title")
	String productTitle;

	@Field("product_apply_allowance_inventory")
	boolean productApplyAllowanceInventory;

	@Field("regular_price_bk")
	Integer regularPriceBk;

	@Field("display_collections_variant")
	Integer displayCollectionsVariant;
}