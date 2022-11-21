package com.journal.florist.backend.feature.customer.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.customer.dto.CustomerMapper;
import com.journal.florist.backend.feature.customer.dto.CustomerRequest;
import com.journal.florist.backend.feature.customer.dto.UpdateCustomerRequest;
import com.journal.florist.backend.feature.customer.model.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService extends HasLogger {

    Page<CustomerMapper> getCustomerByName(String name, Pageable pageable);
    Page<CustomerMapper> findAllCustomer(Pageable pageable);
    Customers getCustomerId(String customerId);
    BaseResponse addCustomer(CustomerRequest customer);
    BaseResponse updateCustomer(UpdateCustomerRequest customer);
    SuccessResponse deleteCustomer(String id);

}
