package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.utils.enums.SexEnum;

import java.time.LocalDate;
import java.util.List;

public record CustomerDto(
        Long id,
        String fullName,
        String email,
        String phone,
        String countryCode,
        LocalDate birthday,
        List<AddressDto> shippingAddresses,
        Double height,
        Double weight,
        SexEnum gender,
        @JsonProperty("is_lock")
        Boolean lock,
        String avatar
) {

}
