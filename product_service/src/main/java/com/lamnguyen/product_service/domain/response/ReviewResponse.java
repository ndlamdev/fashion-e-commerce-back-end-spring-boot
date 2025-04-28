package com.lamnguyen.product_service.domain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse implements Serializable {
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