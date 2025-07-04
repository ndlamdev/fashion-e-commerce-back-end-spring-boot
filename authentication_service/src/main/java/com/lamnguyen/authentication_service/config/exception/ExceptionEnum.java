/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:34 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ExceptionEnum {
	USER_EXIST(90001, "User is exist!"),
	USER_NOT_FOUND(90002, "User not found!"),
	NOT_ACTIVE(90003, "User not active!"),
	UNAUTHORIZED(90004, "Unauthorized"),
	CODE_NOT_FOUND(90005, "Code not found or expired"),
	VERIFY_EXCEEDED_NUMBER(90006, "Exceeded number of authentication attempts"),
	VERIFY_ACCOUNT_FAILED(90005, "Verify account failed"),
	LOGOUT_FAILED(90006, "Logout failed"),
	VERIFICATION_CODE_SENT(90006, "Code has been sent!"),
	ACTIVATED(90007, "Account is active!"),
	ROLE_NOT_FOUND(90008, "Role not found!"),
	ROLE_EXIST(90009, "Role is exist!"),
	TOKEN_NOT_VALID(90010, "Token not valid"),
	ADMIN_CAN_REMOVE_ROLE_MYSELF(90011, "Admin can not remove any role for myself!"),
	ADMIN_CAN_ADD_ROLE_FOR_MYSELF(90012, "Admin can not remove any role for myself!"),
	LOGIN_FAIL(90012, "Admin can not remove any role for myself!"),
	REQUIRE_ACTIVE(90013, "Require verify your account!"),
	REQUIRE_REGISTER(90014, "Require register after login google success!"),
	EMAIL_LOGIN_GOOGLE_NOT_VERIFY(90015, "Email use to login Google not verify!"),
	ACCOUNT_NOT_LINK(90016, "Your account is not link!"),
	GRPC_EXCEPTION(90000, "Internal exception"),
	OLD_PASSWORD_NOT_MATCH(90017, "Old password not match!"),
	NEW_PASSWORD_NOT_DIFFERENT(90018, "New password must be different from old password!"),
	;

	int code;
	String message;

	ExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
