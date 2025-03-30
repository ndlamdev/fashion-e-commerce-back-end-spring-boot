package com.lamnguyen.fashion_e_commerce.config;

import com.lamnguyen.fashion_e_commerce.model.RoleOfUser;
import com.lamnguyen.fashion_e_commerce.model.User;
import com.lamnguyen.fashion_e_commerce.repository.mysql.IRoleOfUserRepository;
import com.lamnguyen.fashion_e_commerce.service.authorization.IRoleService;
import com.lamnguyen.fashion_e_commerce.service.business.user.IUserService;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Order(2)
public class DataInitializer implements CommandLineRunner {

	IUserService userService;
	ApplicationProperty applicationProperty;
	PasswordEncoder passwordEncoder;
	IRoleService iRoleService;
	IRoleOfUserRepository roleOfUserRepository;


	@Override
	public void run(String... args) {
		var nameRoleAdmin = "ADMIN";
		try {
			userService.findUserByEmail(applicationProperty.getEmailAdmin());
		} catch (Exception ignored) {
			var role = iRoleService.getByName(nameRoleAdmin);
			User admin = userService.save(User.builder()
					.email(applicationProperty.getEmailAdmin())
					.password(passwordEncoder.encode(applicationProperty.getPasswordAdmin()))
					.active(true)
					.build());
			roleOfUserRepository.save(RoleOfUser.builder().user(admin).role(role).build());
		}
	}
}
