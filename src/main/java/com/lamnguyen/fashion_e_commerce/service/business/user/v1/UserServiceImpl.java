/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:10 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.business.user.v1;

import com.lamnguyen.fashion_e_commerce.config.exception.ApplicationException;
import com.lamnguyen.fashion_e_commerce.config.exception.ExceptionEnum;
import com.lamnguyen.fashion_e_commerce.model.User;
import com.lamnguyen.fashion_e_commerce.repository.mysql.UserRepository;
import com.lamnguyen.fashion_e_commerce.service.business.user.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;

    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_EXIST));
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
