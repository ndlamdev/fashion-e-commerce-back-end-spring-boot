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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationException extends RuntimeException {
    int code = 400;
    Object messageError;

    private ApplicationException(String message) {
        super(message);
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


    public static ApplicationException createException(ExceptionEnum exceptionEnum) {
        return new ApplicationException(exceptionEnum);
    }

    public static ApplicationException createException(ExceptionEnum exceptionEnum, Object messageError) {
        return new ApplicationException(exceptionEnum, messageError);
    }
}
