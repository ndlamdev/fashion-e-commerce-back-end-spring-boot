package com.lamnguyen.profile_service.controller;

import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.service.IAddressService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/profile/address")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressController {
    IAddressService service;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('USER_GET_ALL_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("get addresses")
    public List<AddressResponse> getAll() {
        return service.getAddresses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_GET_BY_ADDRESS_ID', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address by id")
    public AddressResponse getAddressById(@PathVariable("id") Long id) {
        return service.getAddressById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_SAVE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("save address")
    public AddressResponse saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("id") Long id
    ) {
        return service.saveAddress(request, id);
    }


    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('USER_ADD_ADDRESS', 'ROLE_ADMIN')")
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
}
