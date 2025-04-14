package com.lamnguyen.profile_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.utils.annotation.ValidInternationalPhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidInternationalPhone(phoneField = "phone", countryField = "countryCode")
public record SaveAddressRequest(
        @NotBlank(message = "Require fullName is not blank")
        String fullName,
        @NotBlank(message = "Require phone is not blank")
        String phone,
        @NotNull(message = "Require street is not null")
        String street,
        @NotBlank(message = "Require ward is not blank")
        String ward,
        @NotBlank(message = "Require wardCode is not blank")
        String wardCode,
        @NotBlank(message = "Require district is not blank")
        String district,
        @NotBlank(message = "Require districtCode is not blank")
        String districtCode,
        @NotBlank(message = "Require city is not blank")
        String city,
        @NotBlank(message = "Require cityCode is not blank")
        String cityCode,
        @NotBlank(message = "Require country is not blank")
        String country,
        @NotBlank(message = "Require countryCode is not blank")
        String countryCode,
        @NotNull
        Boolean active
) {
}
