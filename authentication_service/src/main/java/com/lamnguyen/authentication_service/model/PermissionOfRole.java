/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:36 PM - 04/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Table(name = "permission_of_role", uniqueConstraints =
@UniqueConstraint(columnNames = {"permission_id", "role_id"}))
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionOfRole extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "permission_id")
	Permission permission;
	@ManyToOne
	@JoinColumn(name = "role_id")
	Role role;
}
