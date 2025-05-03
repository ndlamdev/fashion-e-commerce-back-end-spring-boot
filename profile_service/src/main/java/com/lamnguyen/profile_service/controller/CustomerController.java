package com.lamnguyen.profile_service.controller;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import com.lamnguyen.profile_service.service.business.ICustomerService;
import com.lamnguyen.profile_service.utils.JwtTokenUtil;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/profile")
public class CustomerController {
    ICustomerService customerService;
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_BASE')")
    @ApiMessageResponse("Get customer by page")
    public ApiPaging<CustomerDto> getAllCustomers(
            @Valid @RequestParam(defaultValue = "0") Integer page,
            @Valid @RequestParam(defaultValue = "12") Integer size
    ) {
        return customerService.getCustomers(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_GET_PROFILE', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get customer by id")
    public CustomerDto getCustomer(@PathVariable @Valid Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('USER_SAVE_PROFILE', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("save customer")
    public SaveCustomerResponse saveCustomer(
            @Valid @RequestBody SaveCustomerRequest saveCustomerRequest,
            @RequestHeader("Authorization") String token
    ) {
        var payload = jwtTokenUtil.getPayloadNotVerify(jwtTokenUtil.decodeTokenNotVerify(token.substring("Bearer ".length())));
        return customerService.saveCustomer(saveCustomerRequest, payload.getUserId());
    }
}
