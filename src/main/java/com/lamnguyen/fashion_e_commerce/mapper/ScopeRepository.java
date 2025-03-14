/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:02 PM - 28/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.mapper;

import com.lamnguyen.fashion_e_commerce.model.Scope;
import com.lamnguyen.fashion_e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long> {
    boolean existsByControllerAndMethodAndUsersContains(String path, String method, User user);

    Optional<Scope> findByController(String path);
}
