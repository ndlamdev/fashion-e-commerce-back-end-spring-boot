/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:52 PM - 19/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.controller;

import com.lamnguyen.fashion_e_commerce.domain.dto.RoleDto;
import com.lamnguyen.fashion_e_commerce.domain.request.ListRoleIdRequest;
import com.lamnguyen.fashion_e_commerce.service.authorization.IAuthorizationService;
import com.lamnguyen.fashion_e_commerce.util.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/authorization")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorizationController {
    IAuthorizationService iAuthorizationService;

    @GetMapping("/{user-id}")
    @ApiMessageResponse("Get all role by user success!")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE')")
    public List<RoleDto> getAll(@PathVariable("user-id") long userId) {
        return iAuthorizationService.getAllRoleByUserContain(userId);
    }

    @GetMapping("/{user-id}/not-contain")
    @ApiMessageResponse("Get all role by user not contain success!")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE')")
    public List<RoleDto> getAllRoleNotContains(@PathVariable("user-id") long userId) {
        return iAuthorizationService.getAllRoleByUserNotContain(userId);
    }

    @PostMapping("/add/{user-id}")
    @ApiMessageResponse("Get all role by user not contain success!")
    @PreAuthorize("hasAnyAuthority('ADD_ROLE_FOR_USER')")
    public List<RoleDto> addRoleForUser(@PathVariable("user-id") long userId, @Valid @RequestBody ListRoleIdRequest request) {
        return iAuthorizationService.assignRole(userId, request.roleIds());
    }

    @DeleteMapping("/{user-id}")
    @ApiMessageResponse("Get all role by user not contain success!")
    @PreAuthorize("hasAnyAuthority('REMOVE_ROLE_OF_USER')")
    public List<RoleDto> removeRoleOfUser(@PathVariable("user-id") long userId, @Valid @RequestBody ListRoleIdRequest request) {
        return iAuthorizationService.removeRole(userId, request.roleIds());
    }
}
