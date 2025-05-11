package com.lamnguyen.profile_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.utils.annotation.ValidInternationalPhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidInternationalPhone(phoneField = "phone", countryField = "countryCode")
public record SaveAddressRequest(
        @NotBlank(message = "Require fullName is not blank")
        @JsonProperty("full_name")
        String fullName,
        @NotBlank(message = "Require phone is not blank")
        String phone,
        @NotNull(message = "Require street is not null")
        String street,
        @NotBlank(message = "Require ward is not blank")
        String ward,
        @NotBlank(message = "Require wardCode is not blank")
        @JsonProperty("ward_id")
        String wardCode,
        @NotBlank(message = "Require district is not blank")
        String district,
        @NotBlank(message = "Require districtCode is not blank")
        @JsonProperty("district_id")
        String districtCode,
        @NotBlank(message = "Require city is not blank")
        String city,
        @NotBlank(message = "Require cityCode is not blank")
        @JsonProperty("city_code")
        String cityCode,
        @NotBlank(message = "Require countryCode is not blank")
        @JsonProperty("country_code")
        String countryCode,
        @NotNull
        Boolean active
) {
}
