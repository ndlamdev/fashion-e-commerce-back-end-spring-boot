package com.lamnguyen.inventory_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariantAndInventoryInfoDto {
    String productId;
    int totalVariants;
    int totalInventories;
}
