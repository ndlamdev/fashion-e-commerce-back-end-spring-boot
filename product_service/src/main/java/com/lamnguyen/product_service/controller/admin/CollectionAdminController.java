/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:50 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller.admin;

import com.lamnguyen.product_service.domain.response.CollectionResponse;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.service.business.ICollectionManageService;
import com.lamnguyen.product_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/collection/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionAdminController {
    ICollectionManageService collectionManageService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_COLLECTION','ROLE_ADMIN')")
    @ApiMessageResponse("Create collection success!")
    public void create(@Valid @RequestBody TitleCollectionRequest request) {
        collectionManageService.create(request);
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAnyAuthority('GET_ALL_PRODUCT_BY_COLLECION_ID','ROLE_ADMIN')")
    List<ProductResponse> getAllProductByCollectionId(@PathVariable("id") String id) {
        return collectionManageService.getAllProductByCollectionId(id);
    }

    @GetMapping
    @ApiMessageResponse("Get all collection success")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_ALL_COLLECTION')")
    public List<CollectionResponse> getAllProducts() {
        return collectionManageService.getAll();
    }
}
