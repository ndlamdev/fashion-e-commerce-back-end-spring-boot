/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:52 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication.v1;

import com.lamnguyen.fashion_e_commerce.config.exception.ApplicationException;
import com.lamnguyen.fashion_e_commerce.config.exception.ExceptionEnum;
import com.lamnguyen.fashion_e_commerce.domain.reponse.RegisterResponse;
import com.lamnguyen.fashion_e_commerce.domain.request.RegisterAccountRequest;
import com.lamnguyen.fashion_e_commerce.domain.request.SetNewPasswordRequest;
import com.lamnguyen.fashion_e_commerce.mapper.UserDetailMapper;
import com.lamnguyen.fashion_e_commerce.mapper.UserMapper;
import com.lamnguyen.fashion_e_commerce.model.JWTPayload;
import com.lamnguyen.fashion_e_commerce.model.User;
import com.lamnguyen.fashion_e_commerce.repository.mysql.UserDetailRepository;
import com.lamnguyen.fashion_e_commerce.service.authentication.IAuthenticationService;
import com.lamnguyen.fashion_e_commerce.service.authentication.IRedisManager;
import com.lamnguyen.fashion_e_commerce.service.business.user.IUserService;
import com.lamnguyen.fashion_e_commerce.service.mail.ISendMailService;
import com.lamnguyen.fashion_e_commerce.util.JwtTokenUtil;
import com.lamnguyen.fashion_e_commerce.util.OtpUtil;
import com.lamnguyen.fashion_e_commerce.util.enums.JwtType;
import com.lamnguyen.fashion_e_commerce.util.property.OtpProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements IAuthenticationService {
    IUserService userService;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    UserDetailRepository userDetailRepository;
    UserDetailMapper userDetailMapper;
    ISendMailService iSendMailService;
    IRedisManager tokenManager;
    JwtTokenUtil jwtTokenUtil;
    OtpProperty.AccountVerification accountVerification;
    OtpProperty.ResetPasswordVerification resetPasswordVerification;


    @Override
    public RegisterResponse register(RegisterAccountRequest request) {
        var user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User userSaved;
        try {
            userSaved = userService.save(user);
        } catch (DataIntegrityViolationException e) {
            throw ApplicationException.createException(ExceptionEnum.USER_EXIST);
        }
        var userDetail = userDetailMapper.toUserDetail(request);
        userDetail.setUser(userSaved);
        userDetailRepository.save(userDetail);
        var userId = userSaved.getId();
        String opt = OtpUtil.generate(6);
        tokenManager.setVerifyAccountCode(userId, opt);
        iSendMailService.sendMailVerifyAccountCode(request.email(), opt);
        return RegisterResponse.builder().email(request.email()).build();
    }

    @Override
    public void verifyAccount(String email, String code) {
        var user = userService.findUserByEmail(email);
        var userId = user.getId();
        var optional = tokenManager.getVerifyAccountCode(userId);
        if (optional.isEmpty()) throw ApplicationException.createException(ExceptionEnum.CODE_NOT_FOUND);
        var total = tokenManager.increaseTotalTryVerifyAccount(userId);
        if (total > accountVerification.getTotalTryValue())
            throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
        if (!code.equals(optional.get()))
            throw ApplicationException.createException(ExceptionEnum.VERIFY_ACCOUNT_FAILED, "The remaining number of authentication attempts is: " + (accountVerification.getTotalTryValue() - total));
        tokenManager.removeVerifyAccountCode(userId);
        user.setActive(true);
        userService.save(user);
    }

    @Override
    public void resendVerifyAccountCode(String email) {
        var user = userService.findUserByEmail(email);
        if (user.isActive()) throw ApplicationException.createException(ExceptionEnum.ACTIVATED);
        var userId = user.getId();
        var optional = tokenManager.getVerifyAccountCode(userId);
        if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
        var total = tokenManager.getTotalResendResetPasswordCode(userId);
        if (total > accountVerification.getTotalResendValue())
            throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
        String opt = OtpUtil.generate(accountVerification.getLength());
        tokenManager.setVerifyAccountCode(userId, opt);
        iSendMailService.sendMailVerifyAccountCode(email, opt);
    }

    @Override
    public void login(String accessToken) {
        var jwtAccessToken = jwtTokenUtil.decodeToken(accessToken);
        var payload = jwtTokenUtil.getPayload(jwtAccessToken);
        var userId = payload.getUserId();
        tokenManager.setAccessTokenId(userId, jwtAccessToken.getId());
        tokenManager.setRefreshTokenId(userId, payload.getRefreshTokenId());
    }

    @Override
    public void logout(String accessToken) {
        accessToken = accessToken.substring("Bearer ".length());
        var jwtAccessToken = jwtTokenUtil.decodeToken(accessToken);
        var payload = jwtTokenUtil.getPayload(jwtAccessToken);
        var userId = jwtTokenUtil.getPayload(jwtTokenUtil.decodeToken(accessToken)).getUserId();
        if (!tokenManager.existAccessTokenId(userId, jwtAccessToken.getId()))
            throw ApplicationException.createException(ExceptionEnum.LOGOUT_FAILED);
        tokenManager.addAccessTokenIdInBlackList(userId, jwtAccessToken.getId());
        tokenManager.addRefreshTokenIdInBlackList(userId, payload.getRefreshTokenId());
        tokenManager.removeAccessTokenId(userId, jwtAccessToken.getId());
        tokenManager.removeRefreshTokenId(userId, payload.getRefreshTokenId());
    }

    @Override
    public void sendResetPasswordCode(String email) {
        var user = userService.findUserByEmail(email);
        var userId = user.getId();
        var optional = tokenManager.getResetPasswordCode(userId);
        if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
        var total = tokenManager.getTotalResendResetPasswordCode(userId);
        if (1 + total > resetPasswordVerification.getTotalResendValue())
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
        if (total > resetPasswordVerification.getTotalTryValue())
            throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
        if (!code.equals(optional.get()))
            throw ApplicationException.createException(ExceptionEnum.VERIFY_ACCOUNT_FAILED, "The remaining number of authentication attempts is: " + (accountVerification.getTotalTryValue() - total));
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
    }

    @Override
    public Jwt renewAccessToken(String refreshToken) {
        var jwt = jwtTokenUtil.decodeToken(refreshToken);
        var simplePayload = jwtTokenUtil.getSimplePayload(jwt);
        if (simplePayload.getType() != JwtType.REFRESH_TOKEN)
            throw ApplicationException.createException(ExceptionEnum.TOKEN_NOT_VALID);
        var user = userService.findById(simplePayload.getUserId());
        return jwtTokenUtil.generateAccessToken(user, JWTPayload.generateForAccessToken(user, jwt.getId()));
    }
}
