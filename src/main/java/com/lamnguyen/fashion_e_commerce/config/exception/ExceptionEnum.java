/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:34 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ExceptionEnum {
    USER_EXIST(HttpStatus.CONFLICT.value(), "User is exist!"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "User not found!"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Code not found or expired"),
    VERIFY_EXCEEDED_NUMBER(HttpStatus.NOT_ACCEPTABLE.value(), "Exceeded number of authentication attempts"),
    VERIFY_ACCOUNT_FAILED(HttpStatus.FAILED_DEPENDENCY.value(), "Verify account failed"),
    LOGOUT_FAILED(HttpStatus.UNAUTHORIZED.value(), "Logout failed"),
    VERIFICATION_CODE_SENT(HttpStatus.CONFLICT.value(), "Code has been sent!"),
    ACTIVATED(HttpStatus.FORBIDDEN.value(), "Account is active!");

    int code;
    String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
