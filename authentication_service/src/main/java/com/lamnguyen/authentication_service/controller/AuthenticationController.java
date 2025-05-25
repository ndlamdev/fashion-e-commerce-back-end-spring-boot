/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:24 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.controller;

import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.reponse.LoginSuccessResponse;
import com.lamnguyen.authentication_service.domain.reponse.TokenResponse;
import com.lamnguyen.authentication_service.domain.request.*;
import com.lamnguyen.authentication_service.service.authentication.IAuthenticationService;
import com.lamnguyen.authentication_service.service.authentication.IFacebookAuthService;
import com.lamnguyen.authentication_service.service.authentication.IGoogleAuthService;
import com.lamnguyen.authentication_service.utils.annotation.ApiMessageResponse;
import com.lamnguyen.authentication_service.utils.helper.JwtTokenUtil;
import com.lamnguyen.authentication_service.utils.property.TokenProperty;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/auth/v1")
public class AuthenticationController {
	IAuthenticationService authenticationService;
	IGoogleAuthService googleAuthService;
	IFacebookAuthService facebookAuthService;
	TokenProperty.AccessTokenProperty accessTokenProperty;
	TokenProperty.RefreshTokenProperty getKeyRefreshToken;

	@PostMapping("/login")
	@ApiMessageResponse(value = "Login success!")
	public LoginSuccessResponse login(HttpSession session) {
		var accessToken = (String) session.getAttribute(accessTokenProperty.getTokenKey());
		var user = authenticationService.login(accessToken);
		return LoginSuccessResponse.builder()
				.user(user)
				.accessToken(accessToken)
				.build();
	}

	@PostMapping("/register")
	@ApiMessageResponse("Register success")
	public void register(@Valid @RequestBody RegisterAccountRequest request) {
		authenticationService.register(request);
	}

	@PostMapping("/verify")
	@ApiMessageResponse("Verify success")
	public void verify(@Valid @RequestBody VerifyAccountRequest request) {
		authenticationService.verifyAccount(request.email(), request.code());
	}

	@PostMapping("/resend-verify")
	@ApiMessageResponse("Resend code verify account success")
	public void resendVerify(@RequestBody @Valid EmailRequest request) {
		authenticationService.resendVerifyAccountCode(request.email());
	}

	@PostMapping("/logout")
	@ApiMessageResponse(value = "Logout success!")
	public void logout(@RequestHeader("Authorization") String accessToken) {
		authenticationService.logout(accessToken);
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
	@PreAuthorize("hasAnyAuthority('AUTH_API_TEST', 'ROLE_ADMIN')")
	@ApiMessageResponse(value = "Test server!")
	public String greeting() {
		return "Hello world!";
	}

	@PostMapping("/reset-password")
	@ApiMessageResponse("Reset password success")
	public void resetPassword(@Valid @RequestBody EmailRequest request) {
		authenticationService.sendResetPasswordCode(request.email());
	}

	@PostMapping("/reset-password/verify")
	@ApiMessageResponse("Verify reset password code success")
	public TokenResponse verifyResetPassword(@Valid @RequestBody VerifyAccountRequest request) {
		var token = authenticationService.verifyResetPasswordCode(request.email(), request.code());
		return TokenResponse.builder().token(token.getTokenValue()).build();
	}

	@PostMapping("/reset-password/set-new-password")
	@ApiMessageResponse("Set new password success")
	public void setNewPassword(@Valid @RequestBody SetNewPasswordRequest request) {
		authenticationService.setNewPassword(request);
	}

	@GetMapping("/validate")
	@ApiMessageResponse("Validate token success!")
	public Mono<ResponseEntity<Void>> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
		var jwt = authenticationService.validate(bearerToken);
		return Mono.just(ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt.getTokenValue()).body(null));
	}

	@PostMapping("/google/login")
	@ApiMessageResponse("Login using google success")
	public LoginSuccessResponse loginWithGoogle(@Valid @RequestBody GoogleAuthRequest request, HttpServletResponse response) {
		var token = googleAuthService.login(request.authCode());
		return getLoginSuccessResponse(response, token);
	}

	@PostMapping("/google/register")
	@ApiMessageResponse("Register with google success")
	public void registerWithGoogle(@RequestBody @Valid RegisterAccountWithGoogleRequest request) {
		googleAuthService.register(request);
	}

	@PostMapping("/facebook/login")
	@ApiMessageResponse("Login using facebook success")
	public LoginSuccessResponse loginWithFacebook(@RequestBody @Valid AccessTokenRequest request, HttpServletResponse response) {
		var token = facebookAuthService.login(request.accessToken());
		return getLoginSuccessResponse(response, token);
	}

	@PostMapping("/facebook/register")
	@ApiMessageResponse("Register with facebook success")
	public void registerWithFacebook(@RequestBody @Valid RegisterAccountWithFacebookRequest request) {
		facebookAuthService.register(request);
	}

	@PostMapping("/change-password")
	@ApiMessageResponse("Change password success")
	public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
		authenticationService.changePassword(request);
	}

	private LoginSuccessResponse getLoginSuccessResponse(HttpServletResponse response, LoginSuccessDto token) {
		var user = authenticationService.login(token.accessToken());
		Cookie refestTokenCookie = new Cookie(getKeyRefreshToken.getTokenKey(), token.refreshToken());
		refestTokenCookie.setMaxAge(getKeyRefreshToken.getExpireToken() * 60000);
		refestTokenCookie.setHttpOnly(true);
		refestTokenCookie.setSecure(true);
		response.addCookie(refestTokenCookie);
		return LoginSuccessResponse.builder()
				.user(user)
				.accessToken(token.accessToken())
				.build();
	}
}
