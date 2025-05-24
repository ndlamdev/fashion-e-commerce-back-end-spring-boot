package com.lamnguyen.profile_service.controller.user;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
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
    public List<AddressResponse> getAll() {
        return service.getAddresses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_GET_BY_ADDRESS_ID', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address by id")
    public AddressResponse getAddressById(@PathVariable("id") Long id) {
        return service.getAddressById(id);
    }

    @GetMapping("/default")
    @PreAuthorize("hasAnyAuthority('USER_GET_BY_ADDRESS_ID', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address default")
    public AddressResponse getDefaultAddress() {
        return service.getDefaultAddress();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_SAVE_ADDRESS', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("save address")
    public AddressResponse saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("id") Long id
    ) {
        return service.saveAddress(request, id);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_ADD_ADDRESS', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("add address")
    public AddressResponse addAddress(
            @RequestBody @Valid SaveAddressRequest request
    ) {
        return service.addAddress(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_DELETE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("delete address")
    public void deleteAddress(@PathVariable Long id) {
        service.deleteAddressById(id);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('USER_SET_DEFAULT_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("set default address")
    public void setDefaultAddress(
            @RequestParam("new") @Valid Long newId,
            @RequestParam("old") @Valid Long oldId
    ) {
         service.setDefaultAddress(oldId, newId);
    }
}
