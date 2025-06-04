/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:46 AM - 30/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseError<T> {
	int code;
	String error;
	T detail;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	StackTraceElement[] trace;
}
