package com.lamnguyen.inventory_service.controller.admin;

import com.lamnguyen.inventory_service.domain.request.UpdateQuantityRequest;
import com.lamnguyen.inventory_service.domain.response.VariantResponse;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import com.lamnguyen.inventory_service.utils.annotation.ApiMessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/inventory/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VariantAdminController {
    IInventoryService inventoryService;

    @GetMapping
    @ApiMessageResponse("Get all inventory success")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_ALL_VARIANT')")
    public List<VariantResponse> getAll() {
        return inventoryService.getAll();
    }

    @PutMapping("/{id}")
    @ApiMessageResponse("Update inventory success")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_UPDATE_VARIANT')")
    public void updateQuantity(@PathVariable("id") String id, @RequestBody UpdateQuantityRequest request) {
        inventoryService.updateQuantity(id, request.quantity());
    }
}
