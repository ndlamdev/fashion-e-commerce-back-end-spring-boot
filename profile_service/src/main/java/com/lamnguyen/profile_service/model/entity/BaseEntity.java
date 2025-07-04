/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:10 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.profile_service.model.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "is_lock", columnDefinition = "bit set default false not null")
	boolean lock;

	@CreatedBy
	@Column(name = "create_by")
	String createBy;

	@CreatedDate
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	LocalDateTime createAt;

	@LastModifiedBy
	@Column(name = "update_by")
	String updateBy;

	@LastModifiedDate
	@Column(columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP")
	LocalDateTime updateAt;
}
