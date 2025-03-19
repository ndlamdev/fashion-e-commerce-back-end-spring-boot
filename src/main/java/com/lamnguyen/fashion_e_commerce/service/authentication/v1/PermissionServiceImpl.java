/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:19 PM - 19/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication.v1;

import com.lamnguyen.fashion_e_commerce.domain.dto.PermissionDto;
import com.lamnguyen.fashion_e_commerce.mapper.PermissionMapper;
import com.lamnguyen.fashion_e_commerce.model.Permission;
import com.lamnguyen.fashion_e_commerce.repository.mysql.PermissionRepository;
import com.lamnguyen.fashion_e_commerce.service.authentication.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionServiceImpl implements IPermissionService {
	PermissionRepository permissionRepository;
	PermissionMapper permissionMapper;

	@Override
	public List<PermissionDto> getAllPermissionDto() {
		return permissionRepository.findAll().stream().map(permissionMapper::toPermissionDto).toList();
	}
	@Override
	public List<Permission> getAllPermission() {
		return permissionRepository.findAll();
	}
}
