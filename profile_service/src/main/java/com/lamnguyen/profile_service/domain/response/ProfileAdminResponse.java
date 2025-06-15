/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:10 AM-15/06/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.domain.response;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileAdminResponse extends ProfileDto {
	int totalOrders;
	double totalSpent;
}
