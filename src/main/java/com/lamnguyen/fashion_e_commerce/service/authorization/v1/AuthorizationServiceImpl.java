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
import com.lamnguyen.fashion_e_commerce.mapper.RoleMapper;
import com.lamnguyen.fashion_e_commerce.model.RoleOfUser;
import com.lamnguyen.fashion_e_commerce.repository.mysql.IRoleOfUserRepository;
import com.lamnguyen.fashion_e_commerce.repository.mysql.IRoleRepository;
import com.lamnguyen.fashion_e_commerce.repository.mysql.IUserRepository;
import com.lamnguyen.fashion_e_commerce.service.authorization.IAuthorizationService;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorizationServiceImpl implements IAuthorizationService {
    IRoleRepository roleRepository;
    IUserRepository userRepository;
    RoleMapper roleMapper;
    IRoleOfUserRepository roleOfUserRepository;
    ApplicationProperty applicationProperty;

    @Override
    @Transactional
    public List<RoleDto> removeRole(long userId, List<Long> roleIds) {
        var user = userRepository.findById(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        if (user.getEmail().equals(applicationProperty.getEmailAdmin()))
            throw ApplicationException.createException(ExceptionEnum.ADMIN_CAN_REMOVE_ROLE_MYSELF);
        roleOfUserRepository.removeAllByUser_IdAndRole_IdIn(userId, roleIds);
        return roleOfUserRepository.findAllByUser_Id(userId).stream().map(it -> roleMapper.toRoleDto(it.getRole())).toList();
    }

    @Override
    @Transactional
    public List<RoleDto> assignRole(long userId, List<Long> roleIds) {
        var user = userRepository.findById(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        if (user.getEmail().equals(applicationProperty.getEmailAdmin()))
            throw ApplicationException.createException(ExceptionEnum.ADMIN_CAN_REMOVE_ROLE_MYSELF);
        var listRole = new ArrayList<RoleDto>();
        for (var roleId : roleIds) {
            var role = roleRepository.findById(roleId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ROLE_NOT_FOUND));
            listRole.add(roleMapper.toRoleDto(role));
        }
        roleOfUserRepository.saveAll(listRole.stream().map(it -> RoleOfUser.builder().user(user).role(roleMapper.toRole(it)).build()).toList());
        return listRole;
    }

    @Override
    public List<RoleDto> getAllRoleByUserNotContain(long userId) {
        return roleOfUserRepository.findAllByUser_IdNotContainRole(userId);
    }

    @Override
    public List<RoleDto> getAllRoleByUserContain(long userId) {
        return roleOfUserRepository.findAllByUser_Id(userId).stream().map(it -> roleMapper.toRoleDto(it.getRole())).toList();
    }
}
