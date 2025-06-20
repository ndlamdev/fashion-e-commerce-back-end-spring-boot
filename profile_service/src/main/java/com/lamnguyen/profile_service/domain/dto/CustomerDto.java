package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.utils.enums.SexEnum;

import java.time.LocalDate;
import java.util.List;

public record CustomerDto(
        Long id,
        @JsonProperty("full_name")
        String fullName,
        String email,
        String phone,
        @JsonProperty("country_code")
        String countryCode,
        LocalDate birthday,
        @JsonProperty("shipping_addresses")
        List<AddressDto> shippingAddresses,
        Double height,
        Double weight,
        SexEnum gender,
        @JsonProperty("is_lock")
        Boolean lock,
        String avatar
) {

}
