/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:12 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication.v1;

import com.lamnguyen.fashion_e_commerce.repository.mysql.UserRepository;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserDetailServiceImpl implements UserDetailsService {
    UserRepository userRepository;
    ApplicationProperty applicationProperty;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username).orElseThrow(() -> new AuthenticationServiceException("User not found!"));
        var authorities = user.getRoles().stream().map(it -> new SimpleGrantedAuthority(applicationProperty.getRolePrefix() + it.getName())).collect(Collectors.toCollection(ArrayList::new));
        authorities.addAll(user.getScopes().stream().map(it -> new SimpleGrantedAuthority(applicationProperty.getPermissionPrefix() + it.getName())).toList());
        return new User(username, user.getPassword(), user.isActive(), true, true, !user.isLock(), authorities);
    }
}
