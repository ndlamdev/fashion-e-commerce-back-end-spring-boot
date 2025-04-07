/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:03 PM - 28/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.repository;

import com.lamnguyen.authentication_service.model.Role;
import com.lamnguyen.authentication_service.model.RoleOfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameAndUserContains(String name, RoleOfUser user);

    boolean existsByName(String name);

    List<Role> findAllByUserContains(RoleOfUser user);

    List<Role> findAllByUserNotContains(RoleOfUser user);

    Optional<Role> findByName(String roleName);

    List<Role> findAllByUserContainsAndIdNotIn(RoleOfUser users, List<Long> ids);
}
