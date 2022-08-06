package com.journal.florist.backend.feature.customer.dto;

import com.journal.florist.backend.feature.utils.Address;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String customerId;
    private String customerName;
    private String customerPhone;
    private String companyName;
    private Address address;
    private String zipCode;
}
