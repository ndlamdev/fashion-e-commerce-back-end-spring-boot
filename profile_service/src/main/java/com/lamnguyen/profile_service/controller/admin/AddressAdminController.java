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
    public AddressDto getAddressById(@PathVariable("id") Long id, @PathVariable("user-id") Long userId) {
        return service.getAddressById(id, userId);
    }

    @PatchMapping("/user/{user-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_SAVE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("save address")
    public AddressDto saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("id") Long id,
            @PathVariable("user-id") Long userId
    ) {
        return service.saveAddress(request, id, userId);
    }

    @PostMapping("/user/{user-id}/address")
    @PreAuthorize("hasAnyAuthority('ADMIN_SAVE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("save address")
    public AddressDto addAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("user-id") Long userId
    ) {
        return service.addAddress(request, userId);
    }


    @DeleteMapping("/user/{user-id}/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("delete address")
    public void deleteAddress(@PathVariable Long id, @PathVariable("user-id") Long userId) {
        service.deleteAddressById(id, userId);
    }

    @PatchMapping("/user/{user-id}/default/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_SET_DEFAULT_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("delete address")
    public void setDefaultAddress(@PathVariable Long id, @PathVariable("user-id") Long userId) {
        service.setDefaultAddress(id, userId);
    }
}
