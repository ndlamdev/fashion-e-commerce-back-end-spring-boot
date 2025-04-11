/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:27 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.authorization.v1;

import com.lamnguyen.authentication_service.config.exception.ApplicationException;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.domain.dto.PermissionDto;
import com.lamnguyen.authentication_service.domain.dto.RoleDto;
import com.lamnguyen.authentication_service.mapper.IPermissionMapper;
import com.lamnguyen.authentication_service.mapper.IRoleMapper;
import com.lamnguyen.authentication_service.model.Permission;
import com.lamnguyen.authentication_service.model.PermissionOfRole;
import com.lamnguyen.authentication_service.model.Role;
import com.lamnguyen.authentication_service.repository.IPermissionRepository;
import com.lamnguyen.authentication_service.repository.IRoleRepository;
import com.lamnguyen.authentication_service.service.authorization.IRoleService;
import com.lamnguyen.authentication_service.util.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RLock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements IRoleService {
	IRoleRepository roleRepository;
	IRoleMapper roleMapper;
	IPermissionMapper permissionMapper;
	IPermissionRepository permissionRepository;
	RedisTemplate<String, RoleDto> roleDtoRedisTemplate;
	RedissionClientUtil redissonClient;

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
		role.getPermissions().add(PermissionOfRole.builder().permission(Permission.builder().id(permissionId).build()).build());
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
		return permissionRepository.findAllByRolesNotContains(PermissionOfRole.builder().role(Role.builder().id(roleId).build()).build()).stream().map(permissionMapper::toPermissionDto).toList();
	}

	@Override
	public List<PermissionDto> getAllPermissionByRoleContains(long roleId) {
		return permissionRepository.findAllByRolesContains(PermissionOfRole.builder().role(Role.builder().id(roleId).build()).build()).stream().map(permissionMapper::toPermissionDto).toList();
	}

	@Override
	public RoleDto getByName(String name) {
		var optional = roleDtoRedisTemplate.opsForValue().get("role:" + name);
		System.out.println(optional);
		if (optional != null) {
			return optional;
		}

		return getByNameDb(name).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ROLE_NOT_FOUND));
	}

	public Optional<RoleDto> getByNameDb(String name) {
		return redissonClient.synchronize(name, name, (data) -> {
			var cached = roleDtoRedisTemplate.opsForValue().get("role:" + name);
			if (cached != null) {
				return Optional.of(cached);
			}

			var role = roleRepository.findByName(name);
			if (role.isEmpty()) return Optional.empty();
			var roleDto = roleMapper.toRoleDto(role.get());
			var permissionDto = role.get().getPermissions().stream().map(it -> permissionMapper.toPermissionDto(it.getPermission())).toList();
			roleDto.setPermissions(permissionDto);
			roleDtoRedisTemplate.opsForValue().set("role:" + name, roleDto, Duration.ofMinutes(60));
			return Optional.of(roleDto);
		}, (data) -> {
			var cached = roleDtoRedisTemplate.opsForValue().get("role:" + name);
			return Optional.ofNullable(cached);
		});
	}
}
