package com.lamnguyen.profile_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.profile_service.domain.dto.AddressDto;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveProfileResponse {
        Long id;

        String fullName;

        String email;

        String phone;

        String countryCode;

        LocalDate birthday;

        List<AddressDto> shippingAddresses;

        Double height;

        Double weight;

        SexEnum gender;
}
