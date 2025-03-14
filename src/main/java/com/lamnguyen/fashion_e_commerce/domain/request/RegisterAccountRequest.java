/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:41 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.fashion_e_commerce.util.annotation.FieldsValueMatch;
import com.lamnguyen.fashion_e_commerce.util.enums.SexEnum;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "Password and confirmPassword not match")
public record RegisterAccountRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
        @Pattern(regexp = ".*[@$!%*?&].*", message = "Password must contain at least one special character (@$!%*?&)")
        String password,
        @NotBlank
        @JsonProperty("confirm-password")
        String confirmPassword,
        @NotBlank
        String fullName,
        @NotBlank
        String phone,
        @NotNull
        SexEnum sexEnum,
        LocalDate birthday
) {

}
