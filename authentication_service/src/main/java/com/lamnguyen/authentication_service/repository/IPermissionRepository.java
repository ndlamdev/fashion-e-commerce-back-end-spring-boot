/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:02 PM - 28/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.repository;

import com.lamnguyen.authentication_service.model.Permission;
import com.lamnguyen.authentication_service.model.PermissionOfRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
	List<Permission> findAllByRolesContains(PermissionOfRole build);

	List<Permission> findAllByRolesNotContains(PermissionOfRole build);
}
