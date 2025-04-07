/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:59 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.mapper;

import com.lamnguyen.authentication_service.domain.request.RegisterAccountRequest;
import com.lamnguyen.authentication_service.event.SaveUserDetailEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {
	SaveUserDetailEvent toUserDetail(RegisterAccountRequest request);
}