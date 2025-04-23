/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:36 PM - 14/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.dto;

import com.lamnguyen.product_service.model.MongoBaseDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class ImageDto extends MongoBaseDocument implements Serializable {
	String src;
}