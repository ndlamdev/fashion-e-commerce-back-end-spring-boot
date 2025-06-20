package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressDto {
	Long id;
	Long userId;
	String fullName;
	String phone;
	String street;
	String ward;
	@JsonProperty("ward_id")
	String wardCode;
	String district;
	@JsonProperty("district_id")
	String districtCode;
	String city;
	String cityCode;
	String country;
	String countryCode;
	Boolean active;
	Boolean lock;
}
