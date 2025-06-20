/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:56 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.repository;

import com.lamnguyen.authentication_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsUserByEmail(String email);

	Optional<User> findByFacebookUserId(String facebookId);

	boolean existsUserByFacebookUserId(String facebookUserId);
}
