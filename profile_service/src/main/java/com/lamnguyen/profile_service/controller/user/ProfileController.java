package com.lamnguyen.profile_service.controller.user;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import com.lamnguyen.profile_service.domain.request.SaveProfileRequest;
import com.lamnguyen.profile_service.service.business.IProfileService;
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
public class ProfileController {
	IProfileService customerService;

	@PutMapping()
	@PreAuthorize("hasAnyAuthority('USER_SAVE_PROFILE', 'ROLE_BASE', 'ROLE_ADMIN')")
	@ApiMessageResponse("save customer")
	public ProfileDto saveCustomer(
			@Valid @RequestBody SaveProfileRequest saveCustomerRequest
	) {
		return customerService.saveProfile(saveCustomerRequest);
	}

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER_GET_PROFILE', 'ROLE_BASE', 'ROLE_ADMIN')")
    @ApiMessageResponse("Get customer by id")
    public ProfileDto getCustomer() {
        return customerService.getProfile();
    }
}
