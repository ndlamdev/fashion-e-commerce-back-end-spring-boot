/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:47 AM - 14/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.request;

import jakarta.validation.constraints.NotNull;

public record EmailRequest(
        @NotNull
        String email
) {
}
