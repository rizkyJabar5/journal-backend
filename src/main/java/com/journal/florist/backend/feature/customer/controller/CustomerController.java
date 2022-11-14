package com.journal.florist.backend.feature.customer.controller;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.feature.customer.dto.CustomerMapper;
import com.journal.florist.backend.feature.customer.dto.CustomerRequest;
import com.journal.florist.backend.feature.customer.dto.UpdateCustomerRequest;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.utils.FilterableCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.CUSTOMER_URL;

@Tag(name = "Customer Endpoint",
        description = "Transaction customer endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping(CUSTOMER_URL)
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Filter customer by customer name")
    @GetMapping(value = "/customer")
    public ResponseEntity<Page<CustomerMapper>> getCustomerName(@RequestParam String name,
                                                                @RequestParam(defaultValue = "1",
                                                                        required = false) int page,
                                                                @RequestParam(defaultValue = "10",
                                                                        required = false) int limit) {
        Pageable filter = FilterableCrudService.getPageableWithSort(
                page - 1,
                limit,
                Sort.by("name").ascending());
        if (name.isBlank()) {
            throw new IllegalException("Parameter name is required");
        }
        Page<CustomerMapper> mapperPage = customerService.getCustomerByName(name, filter);

        return new ResponseEntity<>(mapperPage, HttpStatus.FOUND);
    }

    @Operation(summary = "Fetching all customer in record found")
    @GetMapping(value = "")
    public ResponseEntity<Page<CustomerMapper>> getAllCustomers(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit) {
        Pageable filter = FilterableCrudService.getPageableWithSort(
                page - 1, limit, Sort.by("name").ascending());
        Page<CustomerMapper> response = customerService.findAllCustomer(filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Creating new customer")
    @PostMapping(value = "/add-customer")
    public ResponseEntity<BaseResponse> addCustomer(@RequestBody CustomerRequest request) {
        BaseResponse response = customerService.addCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Updating existing customer")
    @PutMapping(value = "/update-customer")
    public ResponseEntity<BaseResponse> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        if (request.getCustomerId() == null) {
            throw new IllegalException("Customer id is required");
        }
        BaseResponse response = customerService.updateCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleting existing customer")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<SuccessResponse> deleteCustomer(@RequestParam String customerId) {
        SuccessResponse response = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
