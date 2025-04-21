/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:10 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.business.user.v1;

import com.lamnguyen.authentication_service.config.exception.ApplicationException;
import com.lamnguyen.authentication_service.config.exception.ExceptionEnum;
import com.lamnguyen.authentication_service.model.User;
import com.lamnguyen.authentication_service.repository.IUserRepository;
import com.lamnguyen.authentication_service.service.business.user.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements IUserService {
    IUserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
    }

    @Override
    public User findByFacebookUserId(long facebookId) {
        return userRepository.findByFacebookId(facebookId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
    }

    @Override
    public boolean existsUserByFacebookUserId(long facebookUserId) {
        return userRepository.existsUserByFacebookUserId(facebookUserId);;
    }
}
