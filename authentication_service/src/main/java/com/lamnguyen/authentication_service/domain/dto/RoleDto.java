/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:25 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class RoleDto implements Serializable {
	long id;
	String name;
	String describe;
	List<PermissionDto> permissions;
	boolean lock;

	public RoleDto(long id, String name, String describe, List<PermissionDto> permissions, boolean lock) {
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.permissions = permissions;
		this.lock = lock;
	}
}
