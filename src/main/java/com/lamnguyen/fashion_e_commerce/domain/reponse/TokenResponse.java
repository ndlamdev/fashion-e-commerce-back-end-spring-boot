/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:08 AM - 18/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.domain.reponse;

import lombok.Builder;

@Builder
public record TokenResponse(
        String token
) {
}
