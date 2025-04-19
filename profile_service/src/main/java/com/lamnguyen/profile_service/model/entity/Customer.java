package com.lamnguyen.profile_service.model.entity;

import com.lamnguyen.profile_service.utils.enums.SexEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column(nullable = false)
    Long userId;
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    String fullName;
    @Column(unique = true, columnDefinition = "varchar(255) default ''")
    String email;
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    String phone;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'VN'")
    @Builder.Default
    String countryCode = "VN";
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    LocalDate birthday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    List<Address> shippingAddresses;
    @Column(columnDefinition = "double default 0")
    Double height;
    @Column(columnDefinition = "double default 0")
    Double weight;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'MALE'")
    SexEnum gender;
}
