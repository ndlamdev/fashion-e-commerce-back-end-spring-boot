/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.domain.response;

import com.lamnguyen.product_service.model.MongoBaseDocument;
import com.lamnguyen.product_service.utils.enums.CollectionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
public class QuickProductResponse extends MongoBaseDocument implements Serializable {
	String title;

	ImageResponse image;

	Collection collection;

	public record Collection(String id, String title, CollectionType type) {

	}
}
