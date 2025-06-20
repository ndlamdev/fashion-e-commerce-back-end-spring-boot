/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:03 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.utils.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class OtherAuthProperty {
	@Component
	public record FacebookProperty(
			@Value("${application.facebook.app_id}")
			String appIdFacebook,
			@Value("${application.facebook.app_secret}")
			String appSecretFacebook,
			@Value("${application.token.register.facebook.key}")
			String keyRegisterTokenUsingFacebook,
			@Value("${application.token.access.facebook.key}")
			String keyAccessTokenFacebook
	) {

	}

	@Component
	public record GoogleProperty(
			@Value("${application.token.register.expire}")
			int expireRegisterToken,
			@Value("${application.token.register.google.key}")
			String keyRegisterTokenUsingGoogle
	) {

	}
}
