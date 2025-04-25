/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:36 PM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.mapper;

import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import com.lamnguyen.media_service.domain.dto.MediaDto;
import com.lamnguyen.media_service.model.Media;
import com.lamnguyen.media_service.protos.MediaInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface IMediaMapper {
	MediaDto toDto(Media media);

	@Mapping(target = "src", source = "path")
	@Mapping(target = "updateAt", source = "updateAt", qualifiedByName = "toTimestamp")
	@Mapping(target = "createAt", source = "createAt", qualifiedByName = "toTimestamp")
	@Mapping(target = "updateBy", source = "updateBy", qualifiedByName = "toStringValue")
	@Mapping(target = "createBy", source = "createBy", qualifiedByName = "toStringValue")
	MediaInfo toMediaInfo(MediaDto media);

	@Named("toTimestamp")
	default Timestamp toTimestamp(LocalDateTime time) {
		return Timestamp.newBuilder().setSeconds(time.toEpochSecond(java.time.ZoneOffset.UTC)).build();
	}

	@Named("toStringValue")
	default StringValue toStringValue(String data) {
		return StringValue.newBuilder().setValue(data).build();
	}
}
