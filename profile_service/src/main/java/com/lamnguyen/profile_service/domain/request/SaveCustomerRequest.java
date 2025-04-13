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
        @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
        String phone,
        @Past
        LocalDate birthday,
        @Range(min = 140, max = 190)
        Double height,
        @Range(min = 40, max = 90)
        Double weight,
        SexEnum gender
) {
}
