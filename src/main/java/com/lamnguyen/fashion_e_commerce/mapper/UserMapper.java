/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:57 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.mapper;

import com.lamnguyen.fashion_e_commerce.domain.request.RegisterAccountRequest;
import com.lamnguyen.fashion_e_commerce.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterAccountRequest request);
}
