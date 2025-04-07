/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:49 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class ApiSuccessResponse<T> {
    int code;
    String message;
    T data;
}
