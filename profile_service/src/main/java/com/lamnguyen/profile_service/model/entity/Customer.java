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
    String fullName;
    @Column(unique = true)
    String email;
    String phone;
    LocalDate birthday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    List<Address> shippingAddresses;
    Double height;
    Double weight;
    @Enumerated(EnumType.STRING)
    SexEnum gender;
}
