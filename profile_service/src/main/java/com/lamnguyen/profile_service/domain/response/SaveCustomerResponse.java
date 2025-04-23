package com.lamnguyen.profile_service.domain.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.domain.dto.AddressDto;
import com.lamnguyen.profile_service.utils.enums.SexEnum;

import java.time.LocalDate;
import java.util.List;


public record SaveCustomerResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        @JsonProperty("country_code")
        String countryCode,
        LocalDate birthday,
        List<AddressDto> shippingAddresses,
        Double height,
        Double weight,
        SexEnum gender
) {
}
