/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:56 AM - 20/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain.dto;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GooglePayloadDto {
		String fullName;
		String avatar;
		String email;

	public static GooglePayloadDto newInstance(GoogleIdToken.Payload payload) {
		return GooglePayloadDto.builder()
				.fullName(payload.get("name").toString())
				.avatar(payload.get("picture").toString())
				.email(payload.getEmail())
				.build();
	}
}
