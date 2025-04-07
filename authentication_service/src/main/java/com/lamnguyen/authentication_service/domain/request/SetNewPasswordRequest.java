/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:10 AM - 18/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.authentication_service.util.annotation.FieldsValueMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@FieldsValueMatch(field = "password", fieldMatch = "confirmPassword", message = "Password and confirmPassword not match")
public record SetNewPasswordRequest(
        @NotBlank
        String token,
        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
        @Pattern(regexp = ".*[@$!%*?&].*", message = "Password must contain at least one special character (@$!%*?&)")
        String password,
        @JsonProperty("confirm-password")
        String confirmPassword
) {
}
