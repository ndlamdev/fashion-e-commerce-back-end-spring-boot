/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:51 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "scopes")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Scope extends BaseEntity {
    String name;
    String method;
    String controller;
    @Column(name = "`describe`")
    String describe;

    @ManyToMany
    @JoinTable(
            name = "scope_of_role",
            joinColumns = @JoinColumn(name = "scope_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;


    @ManyToMany
    @JoinTable(
            name = "scope_of_user",
            joinColumns = @JoinColumn(name = "scope_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> users;
}
