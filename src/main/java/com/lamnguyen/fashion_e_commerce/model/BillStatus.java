/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:10 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "bill_status")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class BillStatus extends BaseEntity {
    String location;
    String note;
    String status;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    Bill bill;
}