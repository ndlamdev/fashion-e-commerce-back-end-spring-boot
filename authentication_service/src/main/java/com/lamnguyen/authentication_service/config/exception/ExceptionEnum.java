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
	;

	int code;
	String message;

	ExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
