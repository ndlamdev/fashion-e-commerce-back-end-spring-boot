/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.base_service.utils.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.base_service.model.JWTPayload;
import com.lamnguyen.base_service.model.SimplePayload;
import com.lamnguyen.base_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
public class JwtTokenUtil {
	ApplicationProperty applicationProperty;
	ObjectMapper objectMapper;

	public DecodedJWT decodeTokenNotVerify(String token) {
		if (token.startsWith("Bearer "))
			token = token.substring(7);
		return JWT.decode(token);
	}

	public JWTPayload getPayloadNotVerify(DecodedJWT token) {
		var claims = token.getClaim(applicationProperty.getJwtClaim()).asMap();
		return objectMapper.convertValue(claims, JWTPayload.class);
	}

	public SimplePayload getSimplePayloadNotVerify(DecodedJWT token) {
		var claims = token.getClaim(applicationProperty.getJwtClaim()).asMap();
		return objectMapper.convertValue(claims, JWTPayload.class);
	}

	public JWTPayload getPayloadNotVerify(String token) {
		var jwt = decodeTokenNotVerify(token);
		var claims = jwt.getClaim(applicationProperty.getJwtClaim()).asMap();
		return objectMapper.convertValue(claims, JWTPayload.class);
	}

	public SimplePayload getSimplePayloadNotVerify(String token) {
		var jwt = decodeTokenNotVerify(token);
		var claims = jwt.getClaim(applicationProperty.getJwtClaim()).asMap();
		return objectMapper.convertValue(claims, JWTPayload.class);
	}

	public long getUserId(String token) {
		var jwt = decodeTokenNotVerify(token);
		return getPayloadNotVerify(jwt).getUserId();
	}
}
