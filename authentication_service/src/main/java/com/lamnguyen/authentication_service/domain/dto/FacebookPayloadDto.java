/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:56 AM - 20/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacebookPayloadDto {
		String id;
		String name;
		String avatar;
}
