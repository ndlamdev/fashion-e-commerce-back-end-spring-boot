package com.lamnguyen.authentication_service.event;

import com.google.auto.value.AutoValue.Builder;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCartEvent {
	long userId;
}