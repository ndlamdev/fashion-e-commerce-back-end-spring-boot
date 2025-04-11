/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:44 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.mapper;

import com.lamnguyen.authentication_service.domain.dto.PermissionDto;
import com.lamnguyen.authentication_service.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    PermissionDto toPermissionDto(Permission permission);
}