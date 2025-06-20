package com.lamnguyen.profile_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressResponse(
        Long id,
        @JsonProperty("full_name")
        String fullName,
        String phone,
        String street,
        String ward,
        @JsonProperty("ward_id")
        String wardCode,
        String district,
        @JsonProperty("district_id")
        String districtCode,
        String city,
        @JsonProperty("city_code")
        String cityCode,
        @JsonProperty("country_code")
        String countryCode,
        Boolean active
        ) {
}
