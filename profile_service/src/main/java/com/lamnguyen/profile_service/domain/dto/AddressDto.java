package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
        Long id;
        @JsonProperty("customer_id")
        Long customerId;
        String fullName;
        String phone;
        String street;
        String ward;
        String wardCode;
        String district;
        String districtCode;
        String city;
        String cityCode;
        String country;
        String countryCode;
        Boolean active;
        Boolean lock;
}
