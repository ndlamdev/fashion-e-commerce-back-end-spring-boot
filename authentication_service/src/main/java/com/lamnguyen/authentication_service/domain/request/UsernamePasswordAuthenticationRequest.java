/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:29 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.request;

public record UsernamePasswordAuthenticationRequest(
        String email,
        String password
) {
}
