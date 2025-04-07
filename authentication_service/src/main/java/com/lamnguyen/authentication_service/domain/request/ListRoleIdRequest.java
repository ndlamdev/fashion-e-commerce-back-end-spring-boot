/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:01 PM - 19/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import reactor.util.annotation.NonNull;

import java.util.List;

public record ListRoleIdRequest(
		@JsonProperty("role-ids")
		@NonNull
		List<Long> roleIds
) {
}
