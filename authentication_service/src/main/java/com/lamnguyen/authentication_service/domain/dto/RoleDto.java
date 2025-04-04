/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:25 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.dto;

public record RoleDto(
        long id,
        String name,
        boolean lock
) {
}
