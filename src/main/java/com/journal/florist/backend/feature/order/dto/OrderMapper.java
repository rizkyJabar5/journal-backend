package com.journal.florist.backend.feature.order.dto;

import lombok.Data;

@Data
public class OrderMapper {
    private String orderId;
    private String customerName;

    private String city;
    private String state;
    private String street;
    private String zip;

}
