/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:14 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    String email;

    String password;

    String facebookId;

    @Column(columnDefinition = "bit default false")
    boolean active;

    @OneToMany(mappedBy = "user")
    Set<RoleOfUser> roles;
}
