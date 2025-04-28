/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:48 CH-26/04/2025
 * User: Administrator
 **/

package com.lamnguyen.authentication_service.mapper;

import com.google.protobuf.StringValue;
import jdk.jfr.Name;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface IGrpcMapper {
	@Name("convertStringValueToLocalDateTime")
	default LocalDate convertStringValueToLocalDateTime(StringValue value) {
		if (value == null || value.getValue().isEmpty()) {
			return null;
		}

		return LocalDate.parse(value.getValue());
	}

	@Name("convertStringValueToString")
	default String convertStringValueToString(StringValue value) {
		if (value == null || value.getValue().isEmpty()) {
			return null;
		}

		return value.getValue();
	}
}
