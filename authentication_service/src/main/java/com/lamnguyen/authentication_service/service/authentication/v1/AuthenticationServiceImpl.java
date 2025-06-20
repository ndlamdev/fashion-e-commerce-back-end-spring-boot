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
import com.lamnguyen.authentication_service.domain.request.ChangePasswordRequest;
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
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import com.lamnguyen.authentication_service.service.grpc.IProfileUserGrpcClient;
import com.lamnguyen.authentication_service.service.kafka.ICartService;
import com.lamnguyen.authentication_service.service.kafka.IProfileUserService;
import com.lamnguyen.authentication_service.service.mail.ISendMailService;
import com.lamnguyen.authentication_service.service.redis.*;
import com.lamnguyen.authentication_service.utils.enums.JwtType;
import com.lamnguyen.authentication_service.utils.helper.JwtTokenUtil;
import com.lamnguyen.authentication_service.utils.helper.OtpUtil;
import com.lamnguyen.authentication_service.utils.helper.SendMailHelper;
import com.lamnguyen.authentication_service.utils.property.OtpProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements IAuthenticationService {
	IUserService userService;
	PasswordEncoder passwordEncoder;
	IUserMapper userMapper;
	IProfileUserService userDetailService;
	IProfileUserMapper userDetailMapper;
	ISendMailService sendMailService;
	IRegisterCodeRedisManager registerCodeRedisManager;
	IResetPasswordRedisManager resetPasswordCodeRedisManager;
	IAccessTokenRedisManager accessTokenRedisManager;
	IChangePasswordRedisManager changePasswordRedisManager;
	IRefreshTokenRedisManager refreshTokenRedisManager;
	IRoleOfUserRepository roleOfUserRepository;
	JwtTokenUtil jwtTokenUtil;
	OtpProperty.AccountVerification accountVerification;
	OtpProperty.ResetPasswordVerification resetPasswordVerification;
	IProfileUserGrpcClient profileUserGrpcClient;
	ICartService cartService;


	@Override
	public void register(RegisterAccountRequest request) {
		var user = userMapper.toUser(request);
		User oldUser;
		try {
			oldUser = userService.findUserByEmail(user.getEmail());
		} catch (ApplicationException ignored) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			User userSaved = userService.save(user);

			var userId = userSaved.getId();
			// this code use for test
			roleOfUserRepository.save(RoleOfUser.builder().user(userSaved).role(Role.builder().id(2).build()).build());
			//this code use for test

			SendMailHelper.sendMailVerify(registerCodeRedisManager, sendMailService, userId, request.getEmail());
			var userDetail = userDetailMapper.toSaveProfileUserEvent(request);
			userDetail.setUserId(userId);
			userDetailService.save(userDetail);
			cartService.createCart(userId);
			return;
		}
		if (oldUser.isActive())
			throw ApplicationException.createException(ExceptionEnum.USER_EXIST);
		else {
			SendMailHelper.sendMailVerify(registerCodeRedisManager, sendMailService, oldUser.getId(), request.getEmail());
			throw ApplicationException.createException(ExceptionEnum.REQUIRE_ACTIVE);
		}
	}

	@Override
	public void verifyAccount(String email, String code) {
		var user = getVerifyCodeHelper(registerCodeRedisManager, email, code);
		user.setActive(true);
		userService.save(user);
	}

	@Override
	public void resendVerifyAccountCode(String email) {
		var user = userService.findUserByEmail(email);
		if (user.isActive()) throw ApplicationException.createException(ExceptionEnum.ACTIVATED);
		var userId = user.getId();
		var optional = registerCodeRedisManager.getCode(userId);
		if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
		var total = registerCodeRedisManager.getTotalResendCode(userId);
		if (total > accountVerification.getMaxResend())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
		String opt = OtpUtil.generate(accountVerification.getLength());
		registerCodeRedisManager.setCode(userId, opt);
		sendMailService.sendMailVerifyAccountCode(email, opt);
	}

	@Override
	public ProfileUserDto login(String accessToken) {
		var jwtAccessToken = jwtTokenUtil.decodeToken(accessToken);
		var payload = jwtTokenUtil.getPayload(jwtAccessToken);
		var userId = payload.getUserId();
		accessTokenRedisManager.setTokenId(userId, jwtAccessToken.getId());
		refreshTokenRedisManager.setTokenId(userId, payload.getRefreshTokenId());
		return profileUserGrpcClient.findById(userId);
	}

	@Override
	public void logout(String accessToken) {
		accessToken = accessToken.substring("Bearer ".length());
		var jwtAccessToken = jwtTokenUtil.decodeTokenNotVerify(accessToken);
		var payload = jwtTokenUtil.getPayloadNotVerify(jwtAccessToken);
		var userId = payload.getUserId();
		if (!accessTokenRedisManager.existTokenId(userId, jwtAccessToken.getId())) {
			return;
		}
		accessTokenRedisManager.addTokenIdInBlackList(userId, jwtAccessToken.getId());
		refreshTokenRedisManager.addTokenIdInBlackList(userId, payload.getRefreshTokenId());
		accessTokenRedisManager.removeTokenId(userId, jwtAccessToken.getId());
		refreshTokenRedisManager.removeTokenId(userId, payload.getRefreshTokenId());
	}

	@Override
	public void sendResetPasswordCode(String email) {
		var user = userService.findUserByEmail(email);
		if (!user.isActive()) {
			throw ApplicationException.createException(ExceptionEnum.NOT_ACTIVE);
		}
		var userId = user.getId();
		var optional = resetPasswordCodeRedisManager.getCode(userId);
		if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
		var total = resetPasswordCodeRedisManager.getTotalResendCode(userId);
		if (1 + total > resetPasswordVerification.getMaxResend())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);

		String opt = OtpUtil.generate(resetPasswordVerification.getLength());
		resetPasswordCodeRedisManager.setCode(userId, opt);
		sendMailService.sendMailResetPasswordCode(email, opt);
	}

	@Override
	public Jwt verifyResetPasswordCode(String email, String code) {
		var user = getVerifyCodeHelper(resetPasswordCodeRedisManager, email, code);
		return jwtTokenUtil.generateTokenResetPassword(user);
	}

	private User getVerifyCodeHelper(IVerifyCodeRedisManager verifyCodeRedisManager, String email, String code) {
		var user = userService.findUserByEmail(email);
		var userId = user.getId();
		var optional = verifyCodeRedisManager.getCode(userId);
		if (optional.isEmpty()) throw ApplicationException.createException(ExceptionEnum.CODE_NOT_FOUND);
		var total = verifyCodeRedisManager.increaseTotalTry(userId);
		if (total > resetPasswordVerification.getMaxTry())
			throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
		if (!code.equals(optional.get()))
			throw ApplicationException.createException(ExceptionEnum.VERIFY_ACCOUNT_FAILED, "The remaining number of authentication attempts is: " + (accountVerification.getMaxTry() - total));
		verifyCodeRedisManager.removeCode(userId);
		return user;
	}

	@Override
	public void setNewPassword(SetNewPasswordRequest request) {
		var jwt = jwtTokenUtil.decodeToken(request.getToken());
		var simplePayload = jwtTokenUtil.getSimplePayload(jwt);
		if (simplePayload.getType() != JwtType.RESET_PASSWORD)
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		var check = resetPasswordCodeRedisManager.existTokenResetPasswordInBlacklist(simplePayload.getUserId(), jwt.getId());
		if (check) throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		var user = userService.findUserByEmail(simplePayload.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userService.save(user);
		resetPasswordCodeRedisManager.addTokenResetPasswordInBlacklist(simplePayload.getUserId(), jwt.getId());
		changePasswordRedisManager.setDateTimeChangePassword(simplePayload.getUserId(), LocalDateTime.now());
	}

	@Override
	public Jwt renewAccessToken(String refreshToken) {
		var jwt = jwtTokenUtil.decodeTokenNotVerify(refreshToken);
		var simplePayload = jwtTokenUtil.getSimplePayloadNotVerify(jwt);
		if (simplePayload.getType() != JwtType.REFRESH_TOKEN)
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		if (refreshTokenRedisManager.existTokenIdInBlacklist(simplePayload.getUserId(), jwt.getId()))
			throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
		var user = userService.findById(simplePayload.getUserId());
		var token = jwtTokenUtil.generateAccessToken(JWTPayload.generateForAccessToken(user, jwt.getId()));
		accessTokenRedisManager.setTokenId(user.getId(), token.getId());
		return token;
	}

	@Override
	public Jwt validate(String token) {
		var jwtToken = jwtTokenUtil.decodeTokenNotVerify(token.substring(7));
		var jwtPayload = jwtTokenUtil.getPayloadNotVerify(jwtToken);
		var auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		jwtPayload.setRoles(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
		return jwtTokenUtil.encodeToken(jwtToken, jwtPayload);
	}

	@Override
	public void changePassword(ChangePasswordRequest request) {
		var useId = jwtTokenUtil.getUserId();
		var user = userService.findById(useId);
		if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			throw ApplicationException.createException(ExceptionEnum.OLD_PASSWORD_NOT_MATCH);
		}
		if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw ApplicationException.createException(ExceptionEnum.NEW_PASSWORD_NOT_DIFFERENT);
		}
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userService.save(user);
		changePasswordRedisManager.setDateTimeChangePassword(useId, LocalDateTime.now());
	}
}
