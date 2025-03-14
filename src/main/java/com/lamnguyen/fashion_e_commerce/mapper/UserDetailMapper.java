/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:59 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.mapper;

import com.lamnguyen.fashion_e_commerce.domain.request.RegisterAccountRequest;
import com.lamnguyen.fashion_e_commerce.model.UserDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {
    UserDetail toUserDetail(RegisterAccountRequest request);
}