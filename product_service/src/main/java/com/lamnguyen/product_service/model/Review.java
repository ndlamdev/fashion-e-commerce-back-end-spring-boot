package com.lamnguyen.product_service.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Review {
	@Builder.Default
	Integer count = 0;

	@Builder.Default
	Double avg = 9.5;

	@Builder.Default
	@Field("rating_value")
	Double ratingValue = 4.5;

	@Builder.Default
	@Field("rating_count")
	Integer ratingCount = 0;

	@Builder.Default
	@Field("review_count")
	Integer reviewCount = 0;

	@Builder.Default
	Integer bestRating = 5;

	@Builder.Default
	Integer worstRating = 4;
}