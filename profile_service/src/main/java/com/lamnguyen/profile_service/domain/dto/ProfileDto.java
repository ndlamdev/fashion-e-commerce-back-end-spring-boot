package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.utils.enums.SexEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
        Long id;

        @JsonProperty("full_name")
        String fullName;

        String email;

        String phone;

        @JsonProperty("country_code")
        String countryCode;

        LocalDate birthday;

        @JsonProperty("shipping_addresses")
        List<AddressDto> shippingAddresses;

        Double height;

        Double weight;

        SexEnum gender;

        @JsonProperty("is_lock")
        Boolean lock;

        String avatar;
}
