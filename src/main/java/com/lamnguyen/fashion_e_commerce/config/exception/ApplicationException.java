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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationException extends RuntimeException {
    int code = 400;
    Object messageError;

    private ApplicationException(String message) {
        super(message);
    }

    private ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    private ApplicationException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        code = httpStatus.value();
    }

    private ApplicationException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        code = exceptionEnum.getCode();
    }

    private ApplicationException(ExceptionEnum exceptionEnum, Object messageError) {
        super(exceptionEnum.getMessage());
        code = exceptionEnum.getCode();
        this.messageError = messageError;
    }

    public static ApplicationException createException(String message) {
        return new ApplicationException(message);
    }

    public static ApplicationException createException(HttpStatus httpStatus) {
        return new ApplicationException(httpStatus);
    }

    public static ApplicationException createException(ExceptionEnum exceptionEnum) {
        return new ApplicationException(exceptionEnum);
    }

    public static ApplicationException createException(ExceptionEnum exceptionEnum, Object messageError) {
        return new ApplicationException(exceptionEnum, messageError);
    }

    public static ApplicationException createException(int code, String message) {
        return new ApplicationException(code, message);
    }
}
