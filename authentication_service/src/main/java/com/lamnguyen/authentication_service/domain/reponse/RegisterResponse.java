/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:51 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.reponse;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String email
) {
}
