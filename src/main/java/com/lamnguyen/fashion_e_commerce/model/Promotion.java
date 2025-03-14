/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:42 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Promotion extends BaseEntity {
    String name;
    int percent;
    LocalDateTime start;
    LocalDateTime end;

    @OneToMany(mappedBy = "promotion")
    List<ProductOption> productOptions;
}