package com.lamnguyen.profile_service.controller;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import com.lamnguyen.profile_service.service.ICustomerService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/profile")
public class CustomerController {
    ICustomerService customerService;

    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ApiMessageResponse("Get customer by page")
    public ResponseEntity<ApiResponseSuccess<ApiPaging<CustomerDto>>> getAllCustomers(
            @Valid @RequestParam(defaultValue = "0") Integer page,
            @Valid @RequestParam(defaultValue = "12") Integer size
    ) {
        return ResponseEntity.ok(customerService.getCustomers(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_GET_PROFILE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get customer by id")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable @Valid Long id) {

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_SAVE_PROFILE', 'ROLE_ADMIN')")
    @ApiMessageResponse("save customer")
    public ResponseEntity<ApiResponseSuccess<SaveCustomerResponse>> saveCustomer(
            @Valid @RequestBody SaveCustomerRequest saveCustomerRequest,
            @Valid @PathVariable Long id
    ) {
        var response = ApiResponseSuccess.<SaveCustomerResponse>builder()
                .data(customerService.saveCustomer(saveCustomerRequest, id))
                .build();
        return ResponseEntity.ok(response);
    }
}
