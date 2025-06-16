package com.lamnguyen.product_service.domain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminSubProductResponse {
    String id;
    String title;
    int totalVariants;
    int totalInventories;
    boolean lock;
    LocalDateTime createAt;
}
