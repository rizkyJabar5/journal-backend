package com.journal.florist.backend.feature.customer.dto;

import com.journal.florist.backend.feature.utils.Address;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerRequest implements Serializable {
    private String customerName;
    private String customerPhone;
    private String companyName;
    private Address address;
}
