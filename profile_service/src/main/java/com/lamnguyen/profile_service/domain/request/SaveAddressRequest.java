package com.lamnguyen.profile_service.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record SaveAddressRequest(
        @NotNull
        Long id,
        @NotBlank(message = "Require fullName is not blank")
        String fullName,
        @NotBlank(message = "Require phone is not blank")
        String phone,
        @NotBlank(message = "Require city is not blank")
        String city,
        @NotBlank(message = "Require district is not blank")
        String district,
        @NotBlank(message = "Require ward is not blank")
        String ward,
        @NotNull(message = "Require street is not null")
        String street,
        @NotBlank(message = "Require wardCode is not blank")
        String wardCode,
        @NotBlank(message = "Require cityCode is not blank")
        String cityCode,
        @NotBlank(message = "Require districtCode is not blank")
        String districtCode,
        @NotBlank(message = "Require country is not blank")
        String country
) {
}
