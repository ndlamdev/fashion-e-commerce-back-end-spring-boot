/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:39 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
public class MysqlBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "is_lock", columnDefinition = "bit set default false not null")
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
