/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:54 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lamnguyen.product_service.model.MongoBaseDocument;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionSaveRedisDto extends MongoBaseDocument implements Serializable {
	String id;
	String title;
	Set<String> products;
}
