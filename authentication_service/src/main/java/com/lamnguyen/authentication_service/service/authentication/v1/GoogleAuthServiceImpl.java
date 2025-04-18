package com.lamnguyen.authentication_service.service.authentication.v1;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.lamnguyen.authentication_service.config.exception.ApplicationException;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.reponse.RegisterTokenResponse;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithGoogleRequest;
import com.lamnguyen.authentication_service.mapper.IUserDetailMapper;
import com.lamnguyen.authentication_service.mapper.IUserMapper;
import com.lamnguyen.authentication_service.model.JWTPayload;
import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.service.authentication.IGoogleAuthService;
import com.lamnguyen.authentication_service.service.business.user.IUserDetailService;
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import com.lamnguyen.authentication_service.util.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoogleAuthServiceImpl implements IGoogleAuthService {
	GoogleIdTokenVerifier googleIdTokenVerifier;
	IUserService userService;
	JwtTokenUtil jwtTokenUtil;
	IUserMapper userMapper;
	PasswordEncoder passwordEncoder;
	IUserDetailMapper userDetailMapper;
	IUserDetailService userDetailService;
	GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest;

	public GoogleTokenResponse verifyGoogleAuthCode(String authCode) {
		try {
			return googleAuthorizationCodeTokenRequest.setCode(authCode).execute();
		} catch (IOException e) {
			throw ApplicationException.createException(ExceptionEnum.UNAUTHORIZED);
		}
	}

	@Override
	public GoogleIdToken.Payload getPayload(String idTokenString) {
		GoogleIdToken idToken = null;
		try {
			idToken = googleIdTokenVerifier.verify(idTokenString);
		} catch (GeneralSecurityException | IOException | IllegalArgumentException e) {
			e.printStackTrace(System.out);
			throw ApplicationException.createException(ExceptionEnum.UNAUTHORIZED);
		}
		if (idToken == null) throw ApplicationException.createException(ExceptionEnum.UNAUTHORIZED);
		return idToken.getPayload();
	}

	public LoginSuccessDto login(String authCode) {
		var response = verifyGoogleAuthCode(authCode);
		var payload = getPayload(response.getIdToken());
		User user = null;
		try {
			user = userService.findUserByEmail(payload.getEmail());
		} catch (Exception e) {
			var token = jwtTokenUtil.generateRegisterWithGoogleToken(payload);
			throw ApplicationException.createException(ExceptionEnum.REQUIRE_REGISTER, new RegisterTokenResponse(token.getTokenValue()));
		}
		var refreshToken = jwtTokenUtil.generateRefreshToken(user);
		var payloadAccessToken = JWTPayload.generateForAccessToken(user, refreshToken.getId());
		var accessToken = jwtTokenUtil.generateAccessToken(user, payloadAccessToken);
		return new LoginSuccessDto(user.getEmail(), accessToken.getTokenValue(), refreshToken.getTokenValue());
	}

	@Override
	public void register(RegisterAccountWithGoogleRequest request) {
		var payload = jwtTokenUtil.getGoogleIdTokenPayload(request.token());
		if (userService.existsUserByEmail(payload.getEmail())) {
			throw ApplicationException.createException(ExceptionEnum.USER_EXIST);
		}
		var user = userMapper.toUser(request);
		user.setEmail(payload.getEmail());
		user.setPassword(passwordEncoder.encode(request.password()));
		var userSaved = userService.save(user);

		var userDetail = userDetailMapper.toUserDetail(request);
		userDetail.setUserId(userSaved.getId());
		userDetail.setAvatar(payload.get("picture").toString());
		userDetail.setFullName(payload.get("name").toString());
		userDetail.setEmail(payload.getEmail());
		userDetailService.save(userDetail);
	}
}