/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:03 PM - 05/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class MongoBaseEntity {
	@Id
	long id;

	@Field(name = "is_lock")
	boolean lock;

	@CreatedBy
	String createBy;

	@CreatedDate
	LocalDateTime createAt;

	@LastModifiedBy
	String updateBy;

	@LastModifiedDate
	LocalDateTime updateAt;
}