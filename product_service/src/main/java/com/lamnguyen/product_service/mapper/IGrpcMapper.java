/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:09 PM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;

import java.time.*;

@Mapper(componentModel = "spring")
public interface IGrpcMapper {
	ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
	ZoneOffset zoneOffset = ZoneOffset.of("+07:00");

	default String toString(StringValue stringValue) {
		if (StringValue.getDefaultInstance().equals(stringValue)) return null;
		return stringValue.getValue();
	}

	default StringValue toStringValue(String value) {
		if (value == null) return StringValue.getDefaultInstance();
		return StringValue.newBuilder().setValue(value).build();
	}

	default Timestamp formatDateTime(LocalDateTime dateTime) {
		if (dateTime == null) return Timestamp.getDefaultInstance();

		var instant = dateTime.atZone(zoneId).toInstant();

		return Timestamp
				.newBuilder()
				.setSeconds(instant.getEpochSecond())
				.setNanos(instant.getNano())
				.build();
	}

	default LocalDateTime formatDateTime(Timestamp timestamp) {
		if (Timestamp.getDefaultInstance().equals(timestamp))
			return null;

		return LocalDateTime
				.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos(), zoneOffset);
	}

	default Timestamp formatDate(LocalDate localDate) {
		if (localDate == null) return Timestamp.getDefaultInstance();
		var instant = localDate.atStartOfDay(zoneId).toInstant();
		return Timestamp.newBuilder()
				.setSeconds(instant.getEpochSecond())
				.setNanos(instant.getNano())
				.build();
	}

	default LocalDate formatDate(Timestamp timestamp) {
		if (Timestamp.getDefaultInstance().equals(timestamp))
			return null;
		var instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
		return LocalDate.ofInstant(instant, zoneId);
	}
}
