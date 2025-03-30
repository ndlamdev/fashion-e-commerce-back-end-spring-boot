/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:19 PM - 19/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication;

import com.lamnguyen.fashion_e_commerce.domain.dto.PermissionDto;
import com.lamnguyen.fashion_e_commerce.model.Permission;

import java.util.List;

public interface IPermissionService {
	List<PermissionDto> getAllPermissionDto();

	List<Permission> getAllPermission();
}
