package com.lamnguyen.profile_service.domain.response;


import com.lamnguyen.profile_service.model.entity.Address;
import com.lamnguyen.profile_service.utils.enums.SexEnum;

import java.time.LocalDate;
import java.util.List;


public record SaveCustomerResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        LocalDate birthday,
        List<Address> shippingAddresses,
        Double height,
        Double weight,
        SexEnum gender
) {
}
