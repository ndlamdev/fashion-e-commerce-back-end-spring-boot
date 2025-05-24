package com.lamnguyen.profile_service.model.entity;

import com.lamnguyen.profile_service.utils.enums.SexEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "profiles")
public class Profile extends BaseEntity {
	@Column(nullable = false, unique = true, name = "user_id")
	Long userId;

	@Builder.Default
	@Column(nullable = false)
	String fullName = "";

	@Builder.Default
	String email = "";

	@Builder.Default
	@Column(nullable = false)
	String phone = "";

	@Builder.Default
	String avatar = "";

	@Builder.Default
	@Column(nullable = false)
	String countryCode = "VN";

	LocalDate birthday;

	@Builder.Default
	Double height = 0.0;

	@Builder.Default
	Double weight = 0.0;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	SexEnum gender = SexEnum.MALE;
}
