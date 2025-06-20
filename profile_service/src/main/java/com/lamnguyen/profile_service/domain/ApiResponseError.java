/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:46 AM - 30/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.profile_service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
public class ApiResponseError<T> extends ApiResponse<T> {
	String error;
	T detail;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	StackTraceElement[] trace;
}
