/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:02 AM - 26/02/2025
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
@Table(name = "products")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity {
    String name;
    String brand;
    @Column(name = "`describe`")
    String describe;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToMany(mappedBy = "products")
    List<ProductType> type;

    @ManyToMany(mappedBy = "products")
    List<ProductMaterial> materials;

    @OneToMany(mappedBy = "product")
    List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    List<Review> reviews;
}
