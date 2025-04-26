/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:53 CH-26/04/2025
 * User: Administrator
 **/

package com.lamnguyen.profile_service.mapper;

import com.google.protobuf.StringValue;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface IGrpcMapper {
	@Named("convertLocalDateTimeToStringValue")
	default StringValue convertLocalDateTimeToStringValue(LocalDate value) {
		if (value == null) {
			return null;
		}

		return StringValue.of(value.toString());
	}

	@Name("convertStringToStringValue")
	default StringValue convertStringToStringValue(String value) {
		if (value == null) {
			return null;
		}

		return StringValue.of(value);
	}
}
