/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:28 PM - 04/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.event;

import com.lamnguyen.authentication_service.util.enums.SexEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveUserDetailEvent {
	Long userId;
	String email;
	String fullName;
	String avatar;
	String phone;
	SexEnum sexEnum;
	LocalDate birthday;
}
