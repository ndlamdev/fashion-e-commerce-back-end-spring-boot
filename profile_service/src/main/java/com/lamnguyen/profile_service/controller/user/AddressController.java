package com.lamnguyen.profile_service.controller.user;

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
@RequestMapping("/address/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressController {
    IAddressService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_GET_ALL_ADDRESS', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("get addresses")
    public List<AddressDto> getAll() {
        return service.getAddresses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_GET_BY_ADDRESS_ID', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address by id")
    public AddressDto getAddressById(@PathVariable("id") Long addressById) {
        return service.getAddressById(addressById);
    }

    @GetMapping("/default")
    @PreAuthorize("hasAnyAuthority('USER_GET_BY_ADDRESS_ID', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address default")
    public AddressDto getDefaultAddress() {
        return service.getDefaultAddress();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_SAVE_ADDRESS', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Update address")
    public AddressDto saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("id") Long addressId
    ) {
        return service.saveAddress(addressId, request);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_ADD_ADDRESS', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Add address")
    public AddressDto addAddress(
            @RequestBody @Valid SaveAddressRequest request
    ) {
        return service.addAddress(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_DELETE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("delete address")
    public void deleteAddress(@PathVariable("id") Long addressId) {
        service.deleteAddressById(addressId);
    }

    @PatchMapping("/set-default/{id}")
    @PreAuthorize("hasAnyAuthority('USER_SET_DEFAULT_ADDRESS', 'ROLE_ADMIN', 'ROLE_BASE')")
    @ApiMessageResponse("set default address")
    public void setDefaultAddress(@PathVariable("id") Long addressId) {
        service.setDefaultAddress(addressId);
    }
}
