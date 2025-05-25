/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:51 PM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Builder
public class ProfileUserDto {
	@JsonProperty("id")
	long id;
	@JsonProperty("email")
	String email;
	@JsonProperty("full_name")
	String fullName;
	String avatar;
	@JsonProperty("phone")
	String phone;
	@JsonProperty("gender")
	String gender;
	@JsonProperty("birthday")
	LocalDate birthday;
	@JsonProperty("height")
	double height;
	@JsonProperty("weight")
	double weight;
	@JsonProperty("country_code")
	String countryCode;
}
