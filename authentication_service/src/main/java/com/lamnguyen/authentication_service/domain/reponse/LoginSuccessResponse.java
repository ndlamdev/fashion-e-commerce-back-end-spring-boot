/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:28 AM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.domain.reponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.authentication_service.domain.dto.ProfileUserDto;
import lombok.Builder;

@Builder
public record LoginSuccessResponse(@JsonProperty("access-token") String accessToken,
                                   ProfileUserDto user) {
}
