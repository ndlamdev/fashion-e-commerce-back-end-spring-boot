/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:22 PM - 16/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.controller;

import com.lamnguyen.fashion_e_commerce.domain.dto.RoleDto;
import com.lamnguyen.fashion_e_commerce.domain.request.CreateRoleRequest;
import com.lamnguyen.fashion_e_commerce.service.authorization.IAuthorizationService;
import com.lamnguyen.fashion_e_commerce.service.authorization.IRoleService;
import com.lamnguyen.fashion_e_commerce.util.annotation.ApiMessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/role")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleController {
    IRoleService iRoleService;

    @GetMapping
    @ApiMessageResponse("Get all role")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE')")
    public List<RoleDto> getAll() {
        return iRoleService.getAllRole();
    }

    @PostMapping
    @ApiMessageResponse("Create success")
    @PreAuthorize("hasAnyAuthority('CREATE_ROLE')")
    public RoleDto getAll(@RequestBody CreateRoleRequest createRoleRequest) {
        return iRoleService.createRole(createRoleRequest.name());
    }
}
