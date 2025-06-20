/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:31 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config.converter;

import jakarta.annotation.Nonnull;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringToObjectHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    public boolean canRead(@NonNull Class<?> clazz, MediaType mediaType) {
        return String.class.equals(clazz);
    }

    @Override
    public boolean canWrite(@NonNull Class<?> clazz, MediaType mediaType) {
        return String.class.equals(clazz);
    }

    @Override
    @Nonnull
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);
    }

}
