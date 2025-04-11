package com.lamnguyen.profile_service.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record SaveAddressRequest(
        Long id,
        @NotBlank(message = "Require fullName is not blank")
        String fullName,
        @NotBlank(message = "Require phoneNumber is not blank")
        @Size(min = 10, max = 11)
        String phoneNumber,
        @NotBlank(message = "Require city is not blank")
        String city,
        @NotBlank(message = "Require district is not blank")
        String district,
        @NotBlank(message = "Require ward is not blank")
        String ward,
        @NotNull(message = "Require street is not null")
        String street
) {
}
