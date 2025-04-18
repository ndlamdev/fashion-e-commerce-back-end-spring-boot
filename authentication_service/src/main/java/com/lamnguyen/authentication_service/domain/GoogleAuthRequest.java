/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:12 PM - 17/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record GoogleAuthRequest(
		@JsonProperty("auth-code")
		@NotNull
		String authCode
) {
}
