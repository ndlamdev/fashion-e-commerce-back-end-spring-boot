/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:44 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.enums.CollectionType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("collections")
public class Collection extends MongoBaseDocument {
	CollectionType type;

	String title;

	@DocumentReference(lazy = true)
	@Builder.Default
	Set<Product> products = new HashSet<>();
}
