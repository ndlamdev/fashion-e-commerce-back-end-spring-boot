package com.lamnguyen.profile_service.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.profile_service.utils.annotation.ValidInternationalPhone;
import com.lamnguyen.profile_service.utils.enums.SexEnum;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidInternationalPhone(phoneField = "phone", countryField = "countryCode")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveProfileRequest {
    @NotNull(message = "Require fullName is not null")
    String fullName;

    @NotBlank
    String countryCode;

    @NotBlank(message = "Require phoneNumber is not blank")
    String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate birthday;

    @Range(min = 140, max = 190)
    Double height;

    @Range(min = 40, max = 90)
    Double weight;

    @Enumerated
    @NotNull
    SexEnum gender;
}
