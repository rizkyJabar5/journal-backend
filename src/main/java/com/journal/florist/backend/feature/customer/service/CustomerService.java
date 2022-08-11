package com.journal.florist.backend.feature.customer.service;

import com.journal.florist.app.utils.BaseResponse;
import com.journal.florist.app.utils.HasLogger;
import com.journal.florist.backend.feature.customer.dto.CustomerMapper;
import com.journal.florist.backend.feature.customer.dto.CustomerRequest;
import com.journal.florist.backend.feature.customer.dto.UpdateCustomerRequest;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.app.utils.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService extends HasLogger {

    Page<CustomerMapper> getCustomerByName(String name, Pageable pageable);
    Page<CustomerMapper> findAllCustomer(Pageable pageable);
    Customers getCustomers(String customerId);
    BaseResponse addCustomer(CustomerRequest customer);
    BaseResponse updateCustomer(UpdateCustomerRequest customer);
    SuccessResponse deleteCustomer(String id);

}
