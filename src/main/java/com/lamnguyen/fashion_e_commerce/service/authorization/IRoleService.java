/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authorization;

import com.lamnguyen.fashion_e_commerce.domain.dto.PermissionDto;
import com.lamnguyen.fashion_e_commerce.domain.dto.RoleDto;
import com.lamnguyen.fashion_e_commerce.model.Role;

import java.util.List;

public interface IRoleService {
    List<RoleDto> getAllRole();

    void removePermission(long roleId, long permissionId);

    void addPermission(long roleId, long permissionId);

    RoleDto createRole(String name);

    List<PermissionDto> getAllPermissionByRoleNotContains(long roleId);

    List<PermissionDto> getAllPermissionByRoleContains(long roleId);

    Role getByName(String role);
}
