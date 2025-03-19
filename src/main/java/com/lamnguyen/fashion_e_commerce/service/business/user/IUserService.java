/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:09 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.business.user;

import com.lamnguyen.fashion_e_commerce.model.User;

public interface IUserService {
    User findUserByEmail(String email);
    boolean existsUserByEmail(String email);

    User save(User user);

    User findById(long userId);
}
