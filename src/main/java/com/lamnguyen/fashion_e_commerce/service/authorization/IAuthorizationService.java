/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:39 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authorization;

import com.lamnguyen.fashion_e_commerce.domain.dto.RoleDto;

import java.util.List;

public interface IAuthorizationService {
    List<RoleDto> removeRole(long userId, List<Long> roleIds);

    List<RoleDto> assignRole(long userId, List<Long> roleIds);

    List<RoleDto> getAllRoleByUserContain(long userId);

    List<RoleDto> getAllRoleByUserNotContain(long userId);
}
