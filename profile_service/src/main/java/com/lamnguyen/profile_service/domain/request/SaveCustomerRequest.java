package com.lamnguyen.profile_service.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.profile_service.utils.annotation.ValidInternationalPhone;
import com.lamnguyen.profile_service.utils.enums.SexEnum;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@ValidInternationalPhone(phoneField = "phone", countryField = "countryCode")
public record SaveCustomerRequest(
        @NotNull(message = "Require fullName is not null")
        @JsonProperty("full_name")
        String fullName,
        @NotBlank
        @JsonProperty("country_code")
        String countryCode,
        @NotBlank(message = "Require phoneNumber is not blank")
        String phone,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate birthday,
        @Range(min = 140, max = 190)
        Double height,
        @Range(min = 40, max = 90)
        Double weight,
        @Enumerated
        SexEnum gender
) {
}
