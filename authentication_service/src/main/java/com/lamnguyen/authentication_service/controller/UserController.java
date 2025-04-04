/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:08 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.controller;

import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import com.lamnguyen.authentication_service.util.annotation.ApiMessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/v1/user")
public class UserController {
    IUserService userService;

    @GetMapping
    @ApiMessageResponse("Get info user!")
    public User getUser() {
        return userService.findUserByEmail("kimionawa1305@gmail.com");
    }
}
