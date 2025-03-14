/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:59 AM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.model;

import com.lamnguyen.fashion_e_commerce.util.enums.PayTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "bills")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Bill extends BaseEntity {
    String fullName;
    String location;
    String nameLocation;
    String email;
    String phone;
    @Enumerated(EnumType.STRING)
    PayTypeEnum payType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "bill")
    List<BillDetail> productOfBills;

    @OneToMany(mappedBy = "bill")
    List<BillStatus> billStatuses;
}