/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:43 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PermissionDto(
		long id,
		String name,
		String describe,
		boolean lock
) implements Serializable {
}
