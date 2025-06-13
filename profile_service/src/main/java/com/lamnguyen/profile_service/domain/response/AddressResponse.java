package com.lamnguyen.profile_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressResponse {
        Long id;

        String fullName;

        String phone;

        String street;

        String ward;

        @JsonProperty("ward_id")
        String wardCode;

        String district;

        @JsonProperty("district_id")
        String districtCode;

        String city;

        String cityCode;

        String countryCode;

        Boolean active;
}
