package com.lamnguyen.order_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class JWTPayload extends SimplePayload{
    String refreshTokenId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Set<String> roles;
}