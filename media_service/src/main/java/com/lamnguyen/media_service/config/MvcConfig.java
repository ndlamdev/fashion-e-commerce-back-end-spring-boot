/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:46 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.media_service.config;

import com.lamnguyen.media_service.config.converter.StringToObjectHttpMessageConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
	StringToObjectHttpMessageConverter stringToObjectHttpMessageConverter;

	@Override
	public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
		WebMvcConfigurer.super.extendMessageConverters(converters);
		converters.addFirst(stringToObjectHttpMessageConverter);
	}
}
