/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:34 PM - 18/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTokenResponse {
		@JsonProperty("register-token")
		String registerToken;
}
