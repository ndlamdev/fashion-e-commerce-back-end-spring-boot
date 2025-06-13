/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.cart_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto implements Serializable {
	String id;

	boolean lock;

	String title;

	String seoAlias; // path in url: product/seoAlias

	ImageDto image; // Hình ảnh để show card

	@Builder.Default
	boolean available = true; // Có khả dụng hay không
}
