/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:56 AM - 20/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain.dto;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public record GooglePayloadDto(
		String fullName,
		String avatar,
		String email
) {

	public static GooglePayloadDto newInstance(GoogleIdToken.Payload payload) {
		return new GooglePayloadDto(payload.get("name").toString(), payload.get("picture").toString(), payload.getEmail());
	}
}
