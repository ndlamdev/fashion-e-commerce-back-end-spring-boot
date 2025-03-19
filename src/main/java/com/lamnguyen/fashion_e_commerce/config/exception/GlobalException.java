/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:21 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config.exception;


import com.lamnguyen.fashion_e_commerce.domain.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + ": " + it.getDefaultMessage()).collect(Collectors.toCollection(ArrayList::new));
        errors.addAll(exception.getBindingResult().getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList());
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value())
                .message(errors.size() == 1 ? errors.getFirst() : errors)
                .error("Info not validated!")
                .trace(exception.getStackTrace())
                .build());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Object>> handleApplicationException(ApplicationException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value())
                .message(exception.getMessageError())
                .error(exception.getMessage())
                .trace(exception.getStackTrace())
                .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(HttpStatus.PAYMENT_REQUIRED.value())
                .error(HttpStatus.PAYMENT_REQUIRED.name())
                .message(exception.getMessage())
                .trace(exception.getStackTrace())
                .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(AuthorizationDeniedException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.name())
                .message(exception.getMessage())
                .trace(exception.getStackTrace())
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(HttpStatus.PAYMENT_REQUIRED.value())
                .error(HttpStatus.PAYMENT_REQUIRED.name())
                .message(exception.getMessage())
                .trace(exception.getStackTrace())
                .build());
    }


}
