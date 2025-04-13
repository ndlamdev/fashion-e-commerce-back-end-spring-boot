package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.model.entity.Address;
import com.lamnguyen.profile_service.utils.enums.SexEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

public record CustomerDto(
        Long id,
        String fullName,
        String email,
        String phone,
        LocalDate birthday,
        List<AddressDto> shippingAddresses,
        Double height,
        Double weight,
        @JsonProperty("sex")
        SexEnum gender,
        @JsonProperty("is_lock")
        Boolean lock
) {

}
