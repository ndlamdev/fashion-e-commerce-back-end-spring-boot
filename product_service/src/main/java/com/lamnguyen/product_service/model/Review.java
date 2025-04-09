package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "reviews")
public class Review extends MongoBaseEntity {
	Integer count;
	Double avg;
	@Field("rating_value")
	Double ratingValue;
	@Field("rating_count")
	Integer ratingCount;
	@Field("review_count")
	Integer reviewCount;
	Integer bestRating;
	Integer worstRating;
}