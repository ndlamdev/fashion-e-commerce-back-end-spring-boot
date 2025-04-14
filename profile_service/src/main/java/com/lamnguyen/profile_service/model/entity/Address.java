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
    String fullName;
    String phone;
    String street;
    @Column(nullable = false)
    String ward;
    @Column(nullable = false)
    String wardCode;
    @Column(nullable = false)
    String district;
    @Column(nullable = false)
    String districtCode;
    @Column(nullable = false)
    String city;
    @Column(nullable = false)
    String cityCode;
    @Column(nullable = false)
    String country;
    @Column(nullable = false)
    String countryCode;
    @Column(nullable = false)
    Boolean active; // dia chi mac dinh
}
