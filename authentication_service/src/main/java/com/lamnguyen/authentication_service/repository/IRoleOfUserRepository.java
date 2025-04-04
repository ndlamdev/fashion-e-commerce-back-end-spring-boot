/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:19 PM - 19/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.repository;

import com.lamnguyen.authentication_service.domain.dto.RoleDto;
import com.lamnguyen.authentication_service.model.RoleOfUser;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleOfUserRepository extends JpaRepository<RoleOfUser, Long> {
	void removeAllByUser_IdAndRole_IdIn(long userId, List<Long> roleIds);

	List<RoleOfUser> findAllByUser_Id(long userId);

	@Query("""
			SELECT new com.lamnguyen.authentication_service.domain.dto.RoleDto(r.id, r.name, r.lock) FROM Role r
			WHERE r.id NOT IN (SELECT r2.id FROM Role r2 LEFT JOIN RoleOfUser ru2 ON r2.id = ru2.role.id WHERE ru2.user.id = :userId)
			""")
	List<RoleDto> findAllByUser_IdNotContainRole(@PathParam("userId") long userId);
}
