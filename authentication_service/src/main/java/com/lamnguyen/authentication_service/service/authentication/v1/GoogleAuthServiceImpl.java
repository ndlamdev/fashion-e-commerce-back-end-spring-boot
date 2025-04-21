package com.lamnguyen.authentication_service.service.authentication.v1;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.lamnguyen.authentication_service.config.exception.ApplicationException;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.domain.dto.GooglePayloadDto;
import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.reponse.RegisterTokenResponse;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithGoogleRequest;
import com.lamnguyen.authentication_service.mapper.IProfileUserMapper;
import com.lamnguyen.authentication_service.mapper.IUserMapper;
import com.lamnguyen.authentication_service.model.JWTPayload;
import com.lamnguyen.authentication_service.model.Role;
import com.lamnguyen.authentication_service.model.RoleOfUser;
import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.repository.IRoleOfUserRepository;
import com.lamnguyen.authentication_service.service.authentication.IGoogleAuthService;
import com.lamnguyen.authentication_service.service.authentication.IRedisManager;
import com.lamnguyen.authentication_service.service.business.user.IProfileUserService;
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import com.lamnguyen.authentication_service.service.mail.ISendMailService;
import com.lamnguyen.authentication_service.utils.helper.JwtTokenUtil;
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
	IProfileUserMapper userDetailMapper;
	IProfileUserService userDetailService;
	GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest;
	IRoleOfUserRepository roleOfUserRepository;
	IRedisManager tokenManager;
	ISendMailService sendMailService;

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
			if (!payload.getEmailVerified())
				throw ApplicationException.createException(ExceptionEnum.EMAIL_LOGIN_GOOGLE_NOT_VERIFY);

			var token = jwtTokenUtil.generateRegisterWithGoogleToken(GooglePayloadDto.newInstance(payload));
			throw ApplicationException.createException(ExceptionEnum.REQUIRE_REGISTER, new RegisterTokenResponse(token.getTokenValue()));
		}
		var refreshToken = jwtTokenUtil.generateRefreshToken(user);
		var payloadAccessToken = JWTPayload.generateForAccessToken(user, refreshToken.getId());
		var accessToken = jwtTokenUtil.generateAccessToken(payloadAccessToken);
		return new LoginSuccessDto(user.getEmail(), accessToken.getTokenValue(), refreshToken.getTokenValue());
	}

	@Override
	public void register(RegisterAccountWithGoogleRequest request) {
		if (!checkRegisterToken(request.token())) {
			return;
		}

		var jwt = jwtTokenUtil.decodeTokenNotVerify(request.token());
		var payload = jwtTokenUtil.getGooglePayloadDtoNotVerify(request.token());

		var user = userMapper.toUser(request);
		user.setEmail(payload.email());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setActive(true);
		var userSaved = userService.save(user);
		// this code use for test
		roleOfUserRepository.save(RoleOfUser.builder().user(userSaved).role(Role.builder().id(2).build()).build());
		//this code use for test
		var userDetail = userDetailMapper.toUserDetail(payload);
		userDetail.setUserId(userSaved.getId());
		userDetail.setPhone(request.phone());
		userDetailService.save(userDetail);
		tokenManager.setRegisterTokenIdUsingGoogle(jwt.getId());
	}

	private boolean checkRegisterToken(String token) {
		var jwt = jwtTokenUtil.decodeToken(token);

		if (tokenManager.existRegisterTokenIdUsingGoogle(jwt.getId())) {
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		}

		var payload = jwtTokenUtil.getGooglePayloadDtoNotVerify(token);

		try {
			User oldUser = userService.findUserByEmail(payload.email());
			if (oldUser.isActive()) {
				throw ApplicationException.createException(ExceptionEnum.USER_EXIST);
			}

			oldUser.setActive(true);
			userService.save(oldUser);
			return false;
		} catch (Exception ignored) {
			return true;
		}
	}
}