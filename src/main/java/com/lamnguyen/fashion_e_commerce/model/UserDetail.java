/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:20 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.model;

import com.lamnguyen.fashion_e_commerce.util.enums.SexEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "user_details")
@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetail extends BaseEntity {
    String fullName;

    String phone;

    @Enumerated(EnumType.STRING)
    SexEnum sex;

    String avatar;

    LocalDate birthday;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
