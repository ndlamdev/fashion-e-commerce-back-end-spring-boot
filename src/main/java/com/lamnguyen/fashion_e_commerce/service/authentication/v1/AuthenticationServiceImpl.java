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
import com.lamnguyen.fashion_e_commerce.mapper.UserDetailMapper;
import com.lamnguyen.fashion_e_commerce.mapper.UserMapper;
import com.lamnguyen.fashion_e_commerce.service.authentication.IAuthenticationService;
import com.lamnguyen.fashion_e_commerce.service.authentication.IRedisManager;
import com.lamnguyen.fashion_e_commerce.service.business.user.IUserService;
import com.lamnguyen.fashion_e_commerce.service.mail.ISendMailService;
import com.lamnguyen.fashion_e_commerce.util.JwtTokenUtil;
import com.lamnguyen.fashion_e_commerce.util.OtpUtil;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import com.lamnguyen.fashion_e_commerce.util.property.OtpProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements IAuthenticationService {
    IUserService userService;
    ApplicationProperty applicationProperty;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    UserDetailMapper userDetailMapper;
    ISendMailService iSendMailService;
    IRedisManager tokenManager;
    JwtTokenUtil jwtTokenUtil;
    OtpProperty.AccountVerification accountVerification;

    @Override
    public RegisterResponse register(RegisterAccountRequest request) {
        if (userService.existsUserByEmail(request.email()))
            throw ApplicationException.createException(ExceptionEnum.USER_EXIST);
        var user = userMapper.toUser(request);
        var userDetail = userDetailMapper.toUserDetail(request);
        user.setUserDetail(userDetail);
        user.setPassword(passwordEncoder.encode(request.password()));
        var userSaved = userService.save(user);
        var userId = userSaved.getId();
        String opt = OtpUtil.generate(6);
        tokenManager.setVerifyAccountCode(userId, opt);
        iSendMailService.sendMailVerity(request.email(), opt);
        return RegisterResponse.builder().email(request.email()).build();
    }

    @Override
    public void verifyAccount(String email, String code) {
        var user = userService.findUser(email);
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
        var user = userService.findUser(email);
        if (user.isActive()) throw ApplicationException.createException(ExceptionEnum.ACTIVATED);
        var userId = user.getId();
        var optional = tokenManager.getVerifyAccountCode(userId);
        if (optional.isPresent()) throw ApplicationException.createException(ExceptionEnum.VERIFICATION_CODE_SENT);
        var total = tokenManager.getTotalResendVerifyAccount(userId);
        if (total > accountVerification.getTotalResendValue())
            throw ApplicationException.createException(ExceptionEnum.VERIFY_EXCEEDED_NUMBER);
        String opt = OtpUtil.generate(accountVerification.getLength());
        tokenManager.setVerifyAccountCode(userId, opt);
        iSendMailService.sendMailVerity(email, opt);
    }

    @Override
    public void login(String accessToken, String refreshToken) {
        var jwtAccessToken = jwtTokenUtil.decodeToken(accessToken);
        var jwtRefreshToken = jwtTokenUtil.decodeToken(refreshToken);
        var userId = jwtTokenUtil.getPayload(jwtTokenUtil.decodeToken(accessToken)).getUserId();
        tokenManager.setAccessTokenId(userId, jwtAccessToken.getId());
        tokenManager.setRefreshTokenId(userId, jwtRefreshToken.getId());
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        var jwtAccessToken = jwtTokenUtil.decodeToken(accessToken);
        var jwtRefreshToken = jwtTokenUtil.decodeToken(refreshToken);
        var userId = jwtTokenUtil.getPayload(jwtTokenUtil.decodeToken(accessToken)).getUserId();
        if (tokenManager.existAccessTokenId(userId, jwtAccessToken.getId()))
            throw ApplicationException.createException(ExceptionEnum.LOGOUT_FAILED);
        tokenManager.addAccessTokenIdInBlackList(userId, jwtAccessToken.getId());
        tokenManager.addRefreshTokenIdInBlackList(userId, jwtRefreshToken.getId());
        tokenManager.removeAccessTokenId(userId, jwtAccessToken.getId());
        tokenManager.removeRefreshTokenId(userId, jwtRefreshToken.getId());
    }
}
