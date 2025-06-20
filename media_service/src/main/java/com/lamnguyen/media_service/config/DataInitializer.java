package com.lamnguyen.media_service.config;

import com.lamnguyen.media_service.utils.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Order(2)
public class DataInitializer implements CommandLineRunner {
	ApplicationProperty applicationProperty;

	@Override
	@Transactional
	public void run(String... args) {
		var file = new File(applicationProperty.getPathFileManager());
		if (file.exists()) return;
		var ignored = file.mkdirs();
	}
}
