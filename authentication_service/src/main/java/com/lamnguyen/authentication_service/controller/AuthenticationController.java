/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:24 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.controller;

import com.lamnguyen.authentication_service.domain.reponse.LoginSuccessResponse;
import com.lamnguyen.authentication_service.domain.reponse.RegisterResponse;
import com.lamnguyen.authentication_service.domain.reponse.TokenResponse;
import com.lamnguyen.authentication_service.domain.request.EmailRequest;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountRequest;
import com.lamnguyen.authentication_service.domain.request.SetNewPasswordRequest;
import com.lamnguyen.authentication_service.domain.request.VerifyAccountRequest;
import com.lamnguyen.authentication_service.service.authentication.IAuthenticationService;
import com.lamnguyen.authentication_service.util.annotation.ApiMessageResponse;
import com.lamnguyen.authentication_service.util.property.ApplicationProperty;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/v1")
public class AuthenticationController {
	IAuthenticationService authenticationService;
	ApplicationProperty applicationProperty;

	@PostMapping("/login")
	@ApiMessageResponse(value = "Login success!")
	public LoginSuccessResponse login(HttpSession session, Authentication authentication) {
		var accessToken = (String) session.getAttribute(applicationProperty.getKeyAccessToken());
		var email = authentication.getName();
		authenticationService.login(accessToken);
		return LoginSuccessResponse.builder()
				.email(email)
				.accessToken(accessToken)
				.build();
	}

	@PostMapping("/register")
	@ApiMessageResponse("Register success")
	public RegisterResponse register(@Valid @RequestBody RegisterAccountRequest request) {
		return authenticationService.register(request);
	}

	@PostMapping("/verify")
	@ApiMessageResponse("Verify success")
	public Void verify(@Valid @RequestBody VerifyAccountRequest request) {
		authenticationService.verifyAccount(request.email(), request.code());
		return null;
	}

	@PostMapping("/resend-verify")
	@ApiMessageResponse("Resend code verify account success")
	public Void resendVerify(@RequestBody EmailRequest request) {
		authenticationService.resendVerifyAccountCode(request.email());
		return null;
	}

	@PostMapping("/logout")
	@ApiMessageResponse(value = "Logout success!")
	public Void logout(@RequestHeader("Authorization") String accessToken) {
		authenticationService.logout(accessToken);
		return null;
	}

	@PostMapping("/renew-access-token")
	@ApiMessageResponse(value = "Refresh token success!")
	public TokenResponse renewAccessToken(@CookieValue("REFRESH_TOKEN") Cookie refreshToken) {
		return TokenResponse.builder()
				.token(authenticationService
						.renewAccessToken(refreshToken.getValue())
						.getTokenValue())
				.build();
	}

	@GetMapping
	@PreAuthorize("hasAnyAuthority('AUTH_API_TEST')")
	@ApiMessageResponse(value = "Test server!")
	public String greeting() {
		return "Hello world!";
	}

	@PostMapping("/reset-password")
	@ApiMessageResponse("Reset password success")
	public Void resetPassword(@Valid @RequestBody EmailRequest request) {
		authenticationService.sendResetPasswordCode(request.email());
		return null;
	}

	@PostMapping("/reset-password/verify")
	@ApiMessageResponse("Verify reset password code success")
	public TokenResponse verifyResetPassword(@Valid @RequestBody VerifyAccountRequest request) {
		var token = authenticationService.verifyResetPasswordCode(request.email(), request.code());
		return TokenResponse.builder().token(token.getTokenValue()).build();
	}

	@PostMapping("/reset-password/set-new-password")
	@ApiMessageResponse("Set new password success")
	public Void setNewPassword(@Valid @RequestBody SetNewPasswordRequest request) {
		authenticationService.setNewPassword(request);
		return null;
	}
}
