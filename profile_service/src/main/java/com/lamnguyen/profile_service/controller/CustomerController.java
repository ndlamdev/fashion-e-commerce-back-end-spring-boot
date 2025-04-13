package com.lamnguyen.profile_service.controller;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import com.lamnguyen.profile_service.service.ICustomerService;
import com.lamnguyen.profile_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/profile/v1")
public class CustomerController {
    ICustomerService customerService;

    @GetMapping("/")
    @ApiMessageResponse("Get customer by page")
    public ResponseEntity<ApiResponseSuccess<ApiPaging<CustomerDto>>> getAllCustomers(
            @Valid @RequestParam(defaultValue = "0") Integer page,
            @Valid @RequestParam(defaultValue = "12") Integer size
    ) {
        var customers = customerService.getCustomers(page, size);
        var limited = ApiPaging.<CustomerDto>builder()
                .content(customers.getContent())
                .current(customers.getNumber())
                .size(customers.getSize())
                .totalPage(customers.getTotalPages())
                .build();
        var response = ApiResponseSuccess.<ApiPaging<CustomerDto>>builder()
                .data(limited)
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiMessageResponse("Get customer by id")
    public ResponseEntity<ApiResponseSuccess<SaveCustomerResponse>> getCustomer(@PathVariable @Valid Long id) {
        var response = ApiResponseSuccess.<SaveCustomerResponse>builder()
                .data(customerService.getCustomerById(id))
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @ApiMessageResponse("save customer")
    public ResponseEntity<ApiResponseSuccess<SaveCustomerResponse>> saveCustomer(
            @Valid @RequestBody SaveCustomerRequest saveCustomerRequest
    ) {
        var response = ApiResponseSuccess.<SaveCustomerResponse>builder()
                .data(customerService.saveCustomer(saveCustomerRequest))
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
