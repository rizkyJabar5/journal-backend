package com.journal.florist.backend.feature.customer.controller;

import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.feature.customer.dto.CustomerMapper;
import com.journal.florist.backend.feature.customer.dto.CustomerRequest;
import com.journal.florist.backend.feature.customer.dto.UpdateCustomerRequest;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.product.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.journal.florist.app.constant.ApiUrlConstant.CUSTOMER_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(CUSTOMER_URL)
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<Page<CustomerMapper>> getCustomerName(@RequestParam String name,
                                                                Pageable pageable) {
        if (name.isBlank()) {
            throw new IllegalException("Parameter name is required");
        }
        Page<CustomerMapper> mapperPage = customerService.getCustomerByName(name, pageable);

        return new ResponseEntity<>(mapperPage, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerMapper>> getAllCustomers(Pageable pageable) {
        Page<CustomerMapper> response = customerService.findAllCustomer(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-customer")
    public ResponseEntity<BaseResponse> addCustomer(@RequestBody CustomerRequest request) {
        BaseResponse response = customerService.addCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update-customer")
    public ResponseEntity<BaseResponse> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        if (request.getCustomerId() == null) {
            throw new IllegalException("Customer id is required");
        }
        BaseResponse response = customerService.updateCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteCustomer(@RequestParam String customerId) {
        SuccessResponse response = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
