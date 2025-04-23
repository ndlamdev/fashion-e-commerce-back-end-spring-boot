package com.lamnguyen.profile_service.domain.response;

public record AddressResponse(
        Long id,
        String fullName,
        String phone,
        String street,
        String ward,
        String wardCode,
        String district,
        String districtCode,
        String city,
        String cityCode,
        String country,
        String countryCode,
        Boolean active
        ) {
}
