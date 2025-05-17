/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:38 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.base_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class MongoBaseDocument {
	@MongoId
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