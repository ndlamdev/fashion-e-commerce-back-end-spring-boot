/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:51 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles", uniqueConstraints =
@UniqueConstraint(columnNames = {"name"}))
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity {
	String name;
	@Column(name = "`describe`")
	String describe;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	List<PermissionOfRole> permissions;

	@OneToMany(mappedBy = "role")
	List<RoleOfUser> user;

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return Objects.equals(id, role.id);
	}
}
