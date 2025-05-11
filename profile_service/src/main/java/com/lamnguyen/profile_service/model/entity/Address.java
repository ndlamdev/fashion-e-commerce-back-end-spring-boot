package com.lamnguyen.profile_service.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "addresses")
public class Address extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
    @Builder.Default
    @Column(nullable = false)
    String fullName="";
    @Builder.Default
    @Column(nullable = false)
    String phone = "";
    @Builder.Default
    String street = "";
    @Column(nullable = false)
    @Builder.Default
    String ward = "";
    @Column(nullable = false)
    @Builder.Default
    String wardCode = "";
    @Column(nullable = false)
    @Builder.Default
    String district = "";
    @Builder.Default
    @Column(nullable = false)
    String districtCode = "";
    @Column(nullable = false)
    @Builder.Default
    String city = "";
    @Column(nullable = false)
    @Builder.Default
    String cityCode = "";
    @Column(nullable = false)
    @Builder.Default
    String countryCode = "";
    @Builder.Default
    @Column(nullable = false)
    Boolean active = false; // dia chi mac dinh
}
