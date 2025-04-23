/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:47 PM - 17/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain.dto;

public record LoginSuccessDto(
		String email,
		String accessToken,
		String refreshToken
) {
}
