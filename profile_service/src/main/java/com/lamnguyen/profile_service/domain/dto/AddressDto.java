package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressDto(
        Long id,
        @JsonProperty("customer_id")
        Long customerId,
        String street,
        String ward,
        String wardCode,
        String district,
        String districtCode,
        String city,
        String cityCode,
        String country,
        Boolean active,
        Boolean lock
) {
}
