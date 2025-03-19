/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authorization.v1;

import com.lamnguyen.fashion_e_commerce.config.exception.ApplicationException;
import com.lamnguyen.fashion_e_commerce.config.exception.ExceptionEnum;
import com.lamnguyen.fashion_e_commerce.domain.dto.PermissionDto;
import com.lamnguyen.fashion_e_commerce.domain.dto.RoleDto;
import com.lamnguyen.fashion_e_commerce.mapper.PermissionMapper;
import com.lamnguyen.fashion_e_commerce.mapper.RoleMapper;
import com.lamnguyen.fashion_e_commerce.model.Permission;
import com.lamnguyen.fashion_e_commerce.model.Role;
import com.lamnguyen.fashion_e_commerce.repository.mysql.PermissionRepository;
import com.lamnguyen.fashion_e_commerce.repository.mysql.RoleRepository;
import com.lamnguyen.fashion_e_commerce.service.authorization.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements IRoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    @Override
    public List<RoleDto> getAllRole() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleDto).toList();
    }

    @Override
    public void removePermission(long roleId, long permissionId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ROLE_NOT_FOUND));
        role.setPermissions(role.getPermissions().stream().filter(it -> it.getId() != permissionId).toList());
        roleRepository.save(role);
    }

    @Override
    public void addPermission(long roleId, long permissionId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ROLE_NOT_FOUND));
        role.getPermissions().add(Permission.builder().id(permissionId).build());
        roleRepository.save(role);
    }

    @Override
    public RoleDto createRole(String name) {
        Role role;
        try {
            role = roleRepository.save(Role.builder().name(name).build());
        } catch (DataIntegrityViolationException exception) {
            throw ApplicationException.createException(ExceptionEnum.ROLE_EXIST);
        }
        return roleMapper.toRoleDto(role);
    }

    @Override
    public List<PermissionDto> getAllPermissionByRoleNotContains(long roleId) {
        return permissionRepository.findAllByRolesNotContains(Role.builder().id(roleId).build()).stream().map(permissionMapper::toPermissionDto).toList();
    }

    @Override
    public List<PermissionDto> getAllPermissionByRoleContains(long roleId) {
        return permissionRepository.findAllByRolesContains(Role.builder().id(roleId).build()).stream().map(permissionMapper::toPermissionDto).toList();
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ROLE_NOT_FOUND));
    }
}
