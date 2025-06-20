/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:19 PM - 19/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.authentication;

import com.lamnguyen.authentication_service.domain.dto.PermissionDto;
import com.lamnguyen.authentication_service.model.Permission;

import java.util.List;

public interface IPermissionService {
	List<PermissionDto> getAllPermissionDto();

	List<Permission> getAllPermission();
}
