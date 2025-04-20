/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:52 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.authentication.v1;

import com.lamnguyen.authentication_service.config.exception.ApplicationException;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.domain.dto.ProfileUserDto;
import com.lamnguyen.authentication_service.domain.reponse.RegisterResponse;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountRequest;
import com.lamnguyen.authentication_service.domain.request.SetNewPasswordRequest;
import com.lamnguyen.authentication_service.mapper.IProfileUserMapper;
import com.lamnguyen.authentication_service.mapper.IUserMapper;
import com.lamnguyen.authentication_service.model.JWTPayload;
import com.lamnguyen.authentication_service.model.Role;
import com.lamnguyen.authentication_service.model.RoleOfUser;
import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.repository.IRoleOfUserRepository;
import com.lamnguyen.authentication_service.service.authentication.IAuthenticationService;
import com.lamnguyen.authentication_service.service.authentication.IRedisManager;
import com.lamnguyen.authentication_service.service.business.user.IUserDetailService;
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import com.lamnguyen.authentication_service.service.grpc.IProfileUserGrpcClient;
import com.lamnguyen.authentication_service.service.mail.ISendMailService;
import com.lamnguyen.authentication_service.util.JwtTokenUtil;
import com.lamnguyen.authentication_service.util.OtpUtil;
import com.lamnguyen.authentication_service.util.enums.JwtType;
import com.lamnguyen.authentication_service.util.property.OtpProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements IAuthenticationService {
	IUserService userService;
	PasswordEncoder passwordEncoder;
	IUserMapper userMapper;
	IUserDetailService userDetailService;
	IProfileUserMapper userDetailMapper;
	ISendMailService iSendMailService;
	IRedisManager tokenManager;
	IRoleOfUserRepository roleOfUserRepository;
	JwtTokenUtil jwtTokenUtil;
	OtpProperty.AccountVerification accountVerification;
	OtpProperty.ResetPasswordVerification resetPasswordVerification;
	IProfileUserGrpcClient iProfileCostumerGrpcClient;


	@Override
	public RegisterResponse register(RegisterAccountRequest request) {
		var user = userMapper.toUser(request);
		User oldUser = null;
		try {
			oldUser = userService.findUserByEmail(user.getEmail());
		} catch (Exception ignored) {
		}
		if (oldUser != null) {
			if (oldUser.isActive())
				throw ApplicationException.createException(ExceptionEnum.USER_EXIST);
			else {
				sendMailVerify(oldUser.getId(), request);
				throw ApplicationException.createException(ExceptionEnum.REQUIRE_ACTIVE);
			}
		}

		user.setPassword(passwordEncoder.encode(request.password()));
		User userSaved = userService.save(user);

		var userId = userSaved.getId();
		// this code use for test
		roleOfUserRepository.save(RoleOfUser.builder().user(userSaved).role(Role.builder().id(2).build()).build());
		//this code use for test

		sendMailVerify(userId, request);
		var userDetail = userDetailMapper.toUserDetail(request);
		userDetail.setUserId(userId);
		userDetail.setEmail(userSaved.getEmail());
		userDetailService.save(userDetail);
		return RegisterResponse.builder().email(request.email()).build();
	}

	private void sendMailVerify(long userId, RegisterAccountRequest request) {
		String opt = OtpUtil.generate(6);
		tokenManager.setRegisterCode(userId, opt);
		iSendMailService.sendMailVerifyAccountCode(request.email(), opt);
	}

	@Override
	public void verifyAccount(String email, String code) {
		var user = userService.findUserByEmail(email);
		var userId = user.getId();
		var optional = tokenManager.getRegisterCode(userId);
		if (optional.isEmpty()) throw ApplicationException.createException(ExceptionEnum.CODE_NOT_FOUND);
		var total = tokenManager.increaseTotalTryVerifyAccount(userId);
		if (total > accountVerification.getMaxTry())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
		if (!code.equals(optional.get()))
			throw ApplicationException.createException(ExceptionEnum.VERIFY_ACCOUNT_FAILED, "The remaining number of authentication attempts is: " + (accountVerification.getMaxTry() - total));
		tokenManager.removeVerifyRegisterCode(userId);
		user.setActive(true);
		userService.save(user);
	}

	@Override
	public void resendVerifyAccountCode(String email) {
		var user = userService.findUserByEmail(email);
		if (user.isActive()) throw ApplicationException.createException(ExceptionEnum.ACTIVATED);
		var userId = user.getId();
		var optional = tokenManager.getRegisterCode(userId);
		if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
		var total = tokenManager.getTotalResendRegisterCode(userId);
		if (total > accountVerification.getMaxResend())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
		String opt = OtpUtil.generate(accountVerification.getLength());
		tokenManager.setRegisterCode(userId, opt);
		iSendMailService.sendMailVerifyAccountCode(email, opt);
	}

	@Override
	public ProfileUserDto login(String accessToken) {
		var jwtAccessToken = jwtTokenUtil.decodeToken(accessToken);
		var payload = jwtTokenUtil.getPayload(jwtAccessToken);
		var userId = payload.getUserId();
		tokenManager.setAccessTokenId(userId, jwtAccessToken.getId());
		tokenManager.setRefreshTokenId(userId, payload.getRefreshTokenId());
		return iProfileCostumerGrpcClient.findById(userId);
	}

	@Override
	public void logout(String accessToken) {
		accessToken = accessToken.substring("Bearer ".length());
		var jwtAccessToken = jwtTokenUtil.decodeTokenNotVerify(accessToken);
		var payload = jwtTokenUtil.getPayloadNotVerify(jwtAccessToken);
		var userId = payload.getUserId();
		if (!tokenManager.existAccessTokenId(userId, jwtAccessToken.getId())) {
			return;
		}
		tokenManager.addAccessTokenIdInBlackList(userId, jwtAccessToken.getId());
		tokenManager.addRefreshTokenIdInBlackList(userId, payload.getRefreshTokenId());
		tokenManager.removeAccessTokenId(userId, jwtAccessToken.getId());
		tokenManager.removeRefreshTokenId(userId, payload.getRefreshTokenId());
	}

	@Override
	public void sendResetPasswordCode(String email) {
		var user = userService.findUserByEmail(email);
		if (!user.isActive()) {
			throw ApplicationException.createException(ExceptionEnum.NOT_ACTIVE);
		}
		var userId = user.getId();
		var optional = tokenManager.getResetPasswordCode(userId);
		if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
		var total = tokenManager.getTotalResendResetPasswordCode(userId);
		if (1 + total > resetPasswordVerification.getMaxResend())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);

		String opt = OtpUtil.generate(resetPasswordVerification.getLength());
		tokenManager.setResetPasswordCode(userId, opt);
		iSendMailService.sendMailResetPasswordCode(email, opt);
	}

	@Override
	public Jwt verifyResetPasswordCode(String email, String code) {
		var user = userService.findUserByEmail(email);
		var userId = user.getId();
		var optional = tokenManager.getResetPasswordCode(userId);
		if (optional.isEmpty()) throw ApplicationException.createException(ExceptionEnum.CODE_NOT_FOUND);
		var total = tokenManager.increaseTotalTryResetPassword(userId);
		if (total > resetPasswordVerification.getMaxTry())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
		if (!code.equals(optional.get()))
			throw ApplicationException.createException(ExceptionEnum.VERIFY_ACCOUNT_FAILED, "The remaining number of authentication attempts is: " + (accountVerification.getMaxTry() - total));
		tokenManager.removeResetPasswordCode(userId);
		return jwtTokenUtil.generateTokenResetPassword(user);
	}

	@Override
	public void setNewPassword(SetNewPasswordRequest request) {
		var jwt = jwtTokenUtil.decodeToken(request.token());
		var simplePayload = jwtTokenUtil.getSimplePayload(jwt);
		if (simplePayload.getType() != JwtType.RESET_PASSWORD)
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		var check = tokenManager.existTokenResetPasswordInBlacklist(simplePayload.getUserId(), jwt.getId());
		if (check) throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		var user = userService.findUserByEmail(simplePayload.getEmail());
		user.setPassword(passwordEncoder.encode(request.password()));
		userService.save(user);
		tokenManager.addTokenResetPasswordInBlacklist(simplePayload.getUserId(), jwt.getId());
		tokenManager.setDateTimeChangePassword(simplePayload.getUserId(), LocalDateTime.now());
	}

	@Override
	public Jwt renewAccessToken(String refreshToken) {
		var jwt = jwtTokenUtil.decodeTokenNotVerify(refreshToken);
		var simplePayload = jwtTokenUtil.getSimplePayloadNotVerify(jwt);
		if (simplePayload.getType() != JwtType.REFRESH_TOKEN)
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		if (tokenManager.existRefreshInBlacklist(simplePayload.getUserId(), jwt.getId()))
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		var user = userService.findById(simplePayload.getUserId());
		var token = jwtTokenUtil.generateAccessToken(JWTPayload.generateForAccessToken(user, jwt.getId()));
		tokenManager.setAccessTokenId(user.getId(), token.getId());
		return token;
	}
}
