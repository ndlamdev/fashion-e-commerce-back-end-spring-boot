/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:00 PM - 20/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.authentication.v1;

import com.lamnguyen.authentication_service.config.exception.ApplicationException;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.reponse.RegisterTokenResponse;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithFacebookRequest;
import com.lamnguyen.authentication_service.mapper.IProfileUserMapper;
import com.lamnguyen.authentication_service.mapper.IUserMapper;
import com.lamnguyen.authentication_service.model.JWTPayload;
import com.lamnguyen.authentication_service.model.Role;
import com.lamnguyen.authentication_service.model.RoleOfUser;
import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.repository.IRoleOfUserRepository;
import com.lamnguyen.authentication_service.service.authentication.IFacebookAuthService;
import com.lamnguyen.authentication_service.service.authentication.IRedisManager;
import com.lamnguyen.authentication_service.service.business.facebook.IFacebookGraphClient;
import com.lamnguyen.authentication_service.service.business.user.IProfileUserService;
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import com.lamnguyen.authentication_service.utils.helper.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class FacebookAuthServiceImpl implements IFacebookAuthService {
	IFacebookGraphClient facebookGraphClient;
	IUserService userService;
	JwtTokenUtil jwtTokenUtil;
	IUserMapper userMapper;
	PasswordEncoder passwordEncoder;
	IProfileUserService profileUserService;
	IProfileUserMapper profileUserMapper;
	IRedisManager tokenManager;
	IRoleOfUserRepository roleOfUserRepository;

	@Override
	public LoginSuccessDto login(String accessTokenStr) {
		var debugTokenResponse = facebookGraphClient.debugToken(accessTokenStr);
		checkTokenValid(debugTokenResponse, accessTokenStr);

		try {
			User user = userService.findByFacebookUserId(debugTokenResponse.data().userId());
			var refreshToken = jwtTokenUtil.generateRefreshToken(user);
			var payloadAccessToken = JWTPayload.generateForAccessToken(user, refreshToken.getId());
			var accessToken = jwtTokenUtil.generateAccessToken(payloadAccessToken);

			tokenManager.setAccessTokenFacebook(accessTokenStr);

			return new LoginSuccessDto(user.getEmail(), accessToken.getTokenValue(), refreshToken.getTokenValue());
		} catch (Exception e) {
			var token = jwtTokenUtil.generateRegisterWithFacebookUserId(debugTokenResponse.data().userId());
			throw ApplicationException.createException(ExceptionEnum.REQUIRE_REGISTER, new RegisterTokenResponse(token.getTokenValue()));
		}
	}

	private void checkTokenValid(IFacebookGraphClient.DebugTokenResponse debugTokenResponse, String token) {
		if (tokenManager.existAccessTokenFacebook(token)) {
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		}

		if (!debugTokenResponse.data().valid()) throw ApplicationException.createException(ExceptionEnum.UNAUTHORIZED);

		long expiresAt = debugTokenResponse.data().expiresAt();
		boolean isExpired = Instant.ofEpochSecond(expiresAt).isBefore(Instant.now());

		if (isExpired) {
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		}
	}

	@Override
	public void register(RegisterAccountWithFacebookRequest request) {
		if (!preprocessDataRegister(request)) {
			return;
		}

		var jwt = jwtTokenUtil.decodeTokenNotVerify(request.getToken());

		var user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setActive(true);
		User userSaved = userService.save(user);

		var userId = userSaved.getId();
		// this code use for test
		roleOfUserRepository.save(RoleOfUser.builder().user(userSaved).role(Role.builder().id(2).build()).build());
		//this code use for test

		var profileUser = profileUserMapper.toUserDetail(request);
		profileUser.setUserId(userId);
		profileUserService.save(profileUser);
		tokenManager.setRegisterTokenIdUsingFacebook(jwt.getId());
	}

	private boolean preprocessDataRegister(RegisterAccountWithFacebookRequest request) {
		var jwt = jwtTokenUtil.decodeToken(request.getToken());
		if (tokenManager.existRegisterTokenIdUsingFacebook(jwt.getId())) {
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		}

		var facebookUserId = jwtTokenUtil.getFacebookUserIdNotVerify(request.getToken());
		if (userService.existsUserByFacebookUserId(facebookUserId))
			throw ApplicationException.createException(ExceptionEnum.USER_EXIST);

		try {
			User oldUser = userService.findUserByEmail(request.getEmail());

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
