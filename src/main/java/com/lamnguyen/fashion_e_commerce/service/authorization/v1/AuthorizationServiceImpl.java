/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:39 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authorization.v1;

import com.lamnguyen.fashion_e_commerce.config.exception.ApplicationException;
import com.lamnguyen.fashion_e_commerce.config.exception.ExceptionEnum;
import com.lamnguyen.fashion_e_commerce.domain.dto.RoleDto;
import com.lamnguyen.fashion_e_commerce.mapper.PermissionMapper;
import com.lamnguyen.fashion_e_commerce.mapper.RoleMapper;
import com.lamnguyen.fashion_e_commerce.model.Role;
import com.lamnguyen.fashion_e_commerce.model.User;
import com.lamnguyen.fashion_e_commerce.repository.mysql.RoleRepository;
import com.lamnguyen.fashion_e_commerce.repository.mysql.UserRepository;
import com.lamnguyen.fashion_e_commerce.service.authorization.IAuthorizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorizationServiceImpl implements IAuthorizationService {
    RoleRepository roleRepository;
    UserRepository userRepository;
    RoleMapper roleMapper;

    @Override
    public void removeRole(long userId, List<Long> roleIds) {
        var user = userRepository.findById(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        var newRoles = user.getRoles().stream().filter(role -> roleIds.contains(role.getId())).toList();
        user.setRoles(newRoles);
        userRepository.save(user);
    }

    @Override
    public void assignRole(long userId, List<Long> roleIds) {
        var user = userRepository.findById(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        user.getRoles().addAll(roleIds.stream().map(it -> Role.builder().id(it).build()).toList());
        userRepository.save(user);
    }

    @Override
    public List<RoleDto> getAllRoleByUserNotContain(long userId) {
        return roleRepository.findAllByUsersNotContains(User.builder().id(userId).build()).stream().map(roleMapper::toRoleDto).toList();
    }

    @Override
    public List<RoleDto> getAllRoleByUserContain(long userId) {
        return roleRepository.findAllByUsersContains(User.builder().id(userId).build()).stream().map(roleMapper::toRoleDto).toList();
    }
}
