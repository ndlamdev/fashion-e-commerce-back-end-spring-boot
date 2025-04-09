package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@Document("variants")
public class Variant extends MongoBaseEntity {
	@Field("product_id")
	String productId;

	String title;

	@Field("regular_price")
	Integer regularPrice;

	@Field("compare_price")
	Integer comparePrice;

	Integer quantity;

	@Field("option_id_1")
	String option1;
	@Field("option_id_2")
	String option2;
	@Field("option_id_3")
	String option3;
	@Field("option_id_4")
	String option4;

	boolean hide;

	Integer pending;
}