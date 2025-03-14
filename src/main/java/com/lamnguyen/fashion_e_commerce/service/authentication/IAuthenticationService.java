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

public interface IAuthenticationService {
    RegisterResponse register(RegisterAccountRequest request);

    void verifyAccount(String email, String code);

    void resendVerifyAccountCode(String email);

    void login(String accessToken, String refreshToken);

    void logout(String accessToken, String refreshToken);
}
