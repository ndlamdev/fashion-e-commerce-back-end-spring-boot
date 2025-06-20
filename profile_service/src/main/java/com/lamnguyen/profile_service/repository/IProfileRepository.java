package com.lamnguyen.profile_service.repository;

import com.lamnguyen.profile_service.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByUserId(long id);

	List<Profile> findAllByUserIdNot(long userId);
}
