package com.lamnguyen.fashion_e_commerce.config;

import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@Order(1)
public class FlywayConfig {

	@Bean
	CommandLineRunner migrateDatabase(Flyway flyway, EntityManagerFactory entityManagerFactory) {
		return args -> {
			JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
			transactionManager.afterPropertiesSet();
			flyway.migrate();
		};
	}
}
