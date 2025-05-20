/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:28 AM-16/05/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.controller.admin;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.service.business.ICustomerService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/profile/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerAdminController {
	ICustomerService customerService;

	@GetMapping()
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
}
