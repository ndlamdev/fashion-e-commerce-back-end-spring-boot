/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:44 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.mapper;

import com.lamnguyen.fashion_e_commerce.domain.dto.PermissionDto;
import com.lamnguyen.fashion_e_commerce.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toPermissionDto(Permission permission);
}