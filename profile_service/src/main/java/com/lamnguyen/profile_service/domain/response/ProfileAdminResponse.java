/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:10 AM-15/06/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.domain.response;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAdminResponse extends ProfileDto {
	long totalOrders;
	long totalSpent;
}
