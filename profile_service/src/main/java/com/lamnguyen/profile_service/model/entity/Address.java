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
    String street;
    String ward;
    String wardCode;
    String district;
    String districtCode;
    String city;
    String cityCode;
    String country;
    Boolean active;
}
