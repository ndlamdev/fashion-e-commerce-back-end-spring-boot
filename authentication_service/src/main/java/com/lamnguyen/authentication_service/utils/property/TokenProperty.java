/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:43 PM - 11/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.utils.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class TokenProperty {
	String tokenKey;
	String defaultValueToken;
	int expireToken;
	String tokenInBlacklistKey;

	@Component
	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class AccessTokenProperty extends TokenProperty {
		@Value("${application.token.access.key}")
		String tokenKey;
		@Value("${application.token.access.default-token}")
		String defaultValueToken;
		@Value("${application.token.access.expire}")
		int expireToken;
		@Value("${application.token.access.blacklist}")
		String tokenInBlacklistKey;
	}

	@Component
	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class RefreshTokenProperty extends TokenProperty {
		@Value("${application.token.refresh.key}")
		String tokenKey;
		@Value("${application.token.refresh.default-token}")
		String defaultValueToken;
		@Value("${application.token.refresh.expire}")
		int expireToken;
		@Value("${application.token.refresh.blacklist}")
		String tokenInBlacklistKey;
	}

	@Component
	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class ResetPasswordTokenProperty extends TokenProperty {
		@Value("${application.token.reset-password.key}")
		String tokenKey;
		@Value("${application.token.reset-password.default-token}")
		String defaultValueToken;
		@Value("${application.token.reset-password.expire}")
		int expireToken;
		@Value("${application.token.reset-password.blacklist}")
		String tokenInBlacklistKey;
		@Value("${application.token.reset-password.change}")
		String dateTimeChangePasswordKey;
	}
}
