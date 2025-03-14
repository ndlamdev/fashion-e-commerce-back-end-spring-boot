/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:24 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.controller;

import com.lamnguyen.fashion_e_commerce.domain.reponse.LoginSuccessResponse;
import com.lamnguyen.fashion_e_commerce.domain.reponse.RegisterResponse;
import com.lamnguyen.fashion_e_commerce.domain.request.RegisterAccountRequest;
import com.lamnguyen.fashion_e_commerce.domain.request.ResendVerifyAccountCodeRequest;
import com.lamnguyen.fashion_e_commerce.domain.request.VerifyAccountRequest;
import com.lamnguyen.fashion_e_commerce.service.authentication.IAuthenticationService;
import com.lamnguyen.fashion_e_commerce.service.authentication.IRedisManager;
import com.lamnguyen.fashion_e_commerce.util.JwtTokenUtil;
import com.lamnguyen.fashion_e_commerce.util.annotation.ApiMessageResponse;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/v1/auth")
public class AuthenticationController {
    IAuthenticationService authenticationService;
    ApplicationProperty applicationProperty;

    @PostMapping("/login")
    @ApiMessageResponse(value = "Login success!")
    public LoginSuccessResponse login(HttpSession session, Authentication authentication) {
        var refreshToken = (String) session.getAttribute(applicationProperty.getKeyRefreshToken());
        var accessToken = (String) session.getAttribute(applicationProperty.getKeyAccessToken());
        var email = authentication.getName();
        authenticationService.login(accessToken, refreshToken);
        return LoginSuccessResponse.builder()
                .email(email)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterAccountRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/verify")
    @ApiMessageResponse("Verify success")
    public ResponseEntity<Void> verify(@Valid @RequestBody VerifyAccountRequest request) {
        authenticationService.verifyAccount(request.email(), request.code());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/resend-verify")
    @ApiMessageResponse("Resend code verify account success")
    public ResponseEntity<Void> resendVerify(@RequestBody() ResendVerifyAccountCodeRequest request) {
        authenticationService.resendVerifyAccountCode(request.email());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/logout")
    @ApiMessageResponse(value = "Logout success!")
    public ResponseEntity<Void> logout(HttpSession session, @RequestParam("refresh-token") String refreshToken) {
        var accessToken = (String) session.getAttribute("Authorization");
        authenticationService.logout(accessToken, refreshToken);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    @ApiMessageResponse(value = "Test server!")
    public String greeting() {
        return "Hello world!";
    }
}
