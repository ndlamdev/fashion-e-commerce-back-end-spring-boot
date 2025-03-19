/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:48 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication;

import com.lamnguyen.fashion_e_commerce.domain.reponse.RegisterResponse;
import com.lamnguyen.fashion_e_commerce.domain.request.RegisterAccountRequest;
import com.lamnguyen.fashion_e_commerce.domain.request.SetNewPasswordRequest;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IAuthenticationService {
    RegisterResponse register(RegisterAccountRequest request);

    void verifyAccount(String email, String code);

    void resendVerifyAccountCode(String email);

    void login(String accessToken);

    void logout(String accessToken);

    void sendResetPasswordCode(String email);

    Jwt verifyResetPasswordCode(String email, String code);

    void setNewPassword(SetNewPasswordRequest request);

    Jwt renewAccessToken(String refreshToken);
}
