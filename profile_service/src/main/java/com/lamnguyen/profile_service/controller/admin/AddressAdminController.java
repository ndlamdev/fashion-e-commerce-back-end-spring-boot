package com.lamnguyen.profile_service.controller.admin;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.dto.AddressDto;
import com.lamnguyen.profile_service.service.business.IAddressService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/address/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressAdminController {
    IAddressService service;

    @GetMapping("/user/{user-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_GET_CUSTOMERS', 'ROLE_ADMIN')")
    @ApiMessageResponse("get addresses")
    public List<AddressDto> getAll(@PathVariable("user-id") Long userId) {

        return service.getAddresses(userId);
    }

    @GetMapping("/user/{user-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_GET_CUSTOMER_BY_ID', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address by id")
    public AddressDto getAddressById(@PathVariable("id") Long addressId, @PathVariable("user-id") Long userId) {
        return service.getAddressById(userId, addressId);
    }

    @GetMapping("/user/{user-id}/default")
    @PreAuthorize("hasAnyAuthority('USER_GET_BY_ADDRESS_ID', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address default")
    public AddressDto getDefaultAddress(@PathVariable("user-id") Long userId) {
        return service.getDefaultAddress(userId);
    }

    @PutMapping("/user/{user-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_SAVE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("Update address")
    public AddressDto saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("id") Long addressId,
            @PathVariable("user-id") Long userId
    ) {
        return service.saveAddress(userId, addressId, request);
    }

    @PostMapping("/user/{user-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_SAVE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("Add address")
    public AddressDto addAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("user-id") Long userId
    ) {
        return service.addAddress(userId, request);
    }


    @DeleteMapping("/user/{user-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("delete address")
    public void deleteAddress(@PathVariable("id") Long addressId, @PathVariable("user-id") Long userId) {
        service.deleteAddressById(userId, addressId);
    }

    @PatchMapping("/user/{user-id}/set-default/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_SET_DEFAULT_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("set default address")
    public void setDefaultAddress(@PathVariable("id") Long addressId, @PathVariable("user-id") Long userId) {
        service.setDefaultAddress(userId, addressId);
    }
}
