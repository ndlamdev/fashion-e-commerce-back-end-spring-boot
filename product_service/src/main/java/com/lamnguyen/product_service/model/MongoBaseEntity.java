/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:03 PM - 05/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class MongoBaseEntity {
	@Id
	@JsonProperty("id")
	@Field(targetType = FieldType.STRING)
	String id;

	@Field(name = "is_lock")
	@JsonProperty("is_lock")
	boolean lock;

	@CreatedBy
	@JsonProperty("create_by")
	String createBy;

	@CreatedDate
	@JsonProperty("create_at")
	LocalDateTime createAt;

	@LastModifiedBy
	@JsonProperty("update_by")
	String updateBy;

	@LastModifiedDate
	@JsonProperty("update_at")
	LocalDateTime updateAt;
}