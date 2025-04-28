/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:37 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.utils.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "application")
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ApplicationProperty {
    List<String> whiteList;

    @Value(value = "${application.jwt.secret-key}")
    String secretKey;

    @Value(value = "${application.BCrypt-length}")
    int bCryptLength;

    @Value(value = "${application.login-path}")
    String loginPath;

    @Value("${application.jwt.iss}")
    String jwtIss;

    @Value("${application.jwt.claim}")
    String jwtClaim;

    @Value("${application.prefix.role}")
    String rolePrefix;

    @Value("${application.prefix.permission}")
    String permissionPrefix;

	@Value("${application.admin.email}")
	String emailAdmin;
	@Value("${application.admin.password}")
	String passwordAdmin;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	String clientId;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	String clientSecret;

	@Value("${application.facebook.app_id}")
	String appIdFacebook;
	@Value("${application.facebook.app_secret}")
	String appSecretFacebook;
	@Value("${application.token.register.facebook.key}")
	String keyRegisterTokenUsingFacebook;
	@Value("${application.token.access.facebook.key}")
	String keyAccessTokenFacebook;

	@Value("${application.token.register.expire}")
	int expireRegisterToken;
	@Value("${application.token.register.google.key}")
	String keyRegisterTokenUsingGoogle;

}
