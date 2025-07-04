/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:36 PM - 14/04/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class ImageDto implements Serializable {
	String id;
	String src;
}