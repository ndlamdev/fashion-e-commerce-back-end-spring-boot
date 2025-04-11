package com.lamnguyen.profile_service.domain.request;

import com.lamnguyen.profile_service.utils.enums.SexEnum;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
public record SaveCustomerRequest(
        @NotNull(message = "Require id is not null")
        Long id,
        @NotNull(message = "Require fullName is not null")
        String fullName,
        @NotBlank(message = "Require phoneNumber is not blank")
        @Size(min = 10, max = 11)
        String phone,
        @Past
        LocalDate birthday,
        @Range(min= 140, max= 190)
        Double height,
        @Min(40) @Max(90)
        @Range(min = 40, max=90)
        Double weight,
        SexEnum gender
) {
}
