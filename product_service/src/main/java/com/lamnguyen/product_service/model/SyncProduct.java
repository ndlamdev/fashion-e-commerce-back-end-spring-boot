package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class SyncProduct extends MongoBaseEntity {
	String label;
	String value;

	@Field("nhanh_id")
	String nhanhId;

	@Field("item_name")
	String itemName;

	@Field("item_category2")
	String itemCategory2;

	@Field("item_category")
	String itemCategory;
}