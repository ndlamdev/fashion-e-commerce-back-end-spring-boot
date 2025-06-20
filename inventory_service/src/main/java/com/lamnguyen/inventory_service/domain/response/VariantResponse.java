package com.lamnguyen.inventory_service.domain.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.inventory_service.utils.enums.OptionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VariantResponse {
    String id;
    String productId;
    String title;
    String sku;
    double regularPrice;
    double comparePrice;
    Map<OptionType, String> options;
    int quantity;
    boolean lock;
    LocalDateTime createAt;
}
