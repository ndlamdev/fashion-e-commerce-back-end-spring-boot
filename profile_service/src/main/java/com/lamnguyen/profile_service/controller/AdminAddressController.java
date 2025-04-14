package com.lamnguyen.profile_service.controller;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.service.IAddressService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/profile")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AdminAddressController {
    IAddressService service;

    @GetMapping("/{customer-id}/address/")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE', 'ROLE_ADMIN')")
    @ApiMessageResponse("get addresses")
    public List<AddressResponse> getAll(@PathVariable("customer-id") Long customerId) {
        return service.getAddresses(customerId);
    }

    @GetMapping("/{customer-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get address by id")
    public AddressResponse getAddressById(@PathVariable("id") Long id, @PathVariable("customer-id") Long CustomerId) {
        return service.getAddressById(id, CustomerId);
    }

    @PatchMapping("/{customer-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE', 'ROLE_ADMIN')")
    @ApiMessageResponse("save address")
    public AddressResponse saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("id") Long id,
            @PathVariable("customer-id") Long CustomerId
    ) {
        return service.saveAddress(request, id, CustomerId);
    }

    @PostMapping("/{customer-id}/address/")
    @PreAuthorize("hasAnyAuthority('GET_ALL_ROLE', 'ROLE_ADMIN')")
    @ApiMessageResponse("add address")
    public AddressResponse saveAddress(
            @RequestBody @Valid SaveAddressRequest request,
            @PathVariable("customer-id") Long CustomerId
    ) {
        return service.addAddress(request, CustomerId);
    }

    @PostMapping("/address/")
    @PreAuthorize("hasAnyAuthority('USER_ADD_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("add address")
    public AddressResponse saveAddress(
            @RequestBody @Valid SaveAddressRequest request
    ) {
        return service.addAddress(request);
    }


    @DeleteMapping("/{customer-id}/address/{id}")
    @PreAuthorize("hasAnyAuthority('USER_DELETE_ADDRESS', 'ROLE_ADMIN')")
    @ApiMessageResponse("delete address")
    public void deleteAddress(@PathVariable Long id, @PathVariable("customer-id") Long CustomerId) {
        service.deleteAddressById(id, CustomerId);
    }
}
