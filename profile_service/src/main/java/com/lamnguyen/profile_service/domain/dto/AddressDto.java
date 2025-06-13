package com.lamnguyen.profile_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
	Long id;
	@JsonProperty("user_id")
	Long userId;
	@JsonProperty("full_name")
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
	@JsonProperty("city_code")
	String cityCode;
	String country;
	@JsonProperty("country_code")
	String countryCode;
	Boolean active;
	Boolean lock;
}
