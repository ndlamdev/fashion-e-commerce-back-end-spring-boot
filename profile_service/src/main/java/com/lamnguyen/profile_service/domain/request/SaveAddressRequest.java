package com.lamnguyen.profile_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.profile_service.utils.annotation.ValidInternationalPhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidInternationalPhone(phoneField = "phone", countryField = "countryCode")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveAddressRequest {
        @NotBlank(message = "Require fullName is not blank")
        String fullName;

        @NotBlank(message = "Require phone is not blank")
        String phone;

        @NotNull(message = "Require street is not null")
        String street;

        @NotBlank(message = "Require ward is not blank")
        String ward;

        @NotBlank(message = "Require wardCode is not blank")
        @JsonProperty("ward_id")
        String wardCode;

        @NotBlank(message = "Require district is not blank")
        String district;

        @NotBlank(message = "Require districtCode is not blank")
        @JsonProperty("district_id")
        String districtCode;

        @NotBlank(message = "Require city is not blank")
        String city;

        @NotBlank(message = "Require cityCode is not blank")
        String cityCode;

        @NotBlank(message = "Require countryCode is not blank")
        String countryCode;

        @NotNull
        Boolean active;
}
