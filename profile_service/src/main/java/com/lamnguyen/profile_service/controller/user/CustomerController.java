package com.lamnguyen.profile_service.controller.user;

import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.service.business.ICustomerService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/profile/v1")
public class CustomerController {
	ICustomerService customerService;

	@PutMapping()
	@PreAuthorize("hasAnyAuthority('USER_SAVE_PROFILE', 'ROLE_BASE', 'ROLE_ADMIN')")
	@ApiMessageResponse("save customer")
	public CustomerDto saveCustomer(
			@Valid @RequestBody SaveCustomerRequest saveCustomerRequest
	) {
		return customerService.saveCustomer(saveCustomerRequest);
	}

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER_GET_PROFILE', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get customer by id")
    public CustomerDto getCustomer() {
        return customerService.getCustomer();
    }
}
