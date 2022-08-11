package com.journal.florist.backend.feature.order.dto;

import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.utils.Address;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddOrderRequest implements Serializable {

    private String customerId;
    private String productId;
    private Integer quantity;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private String notes;
    private String recipientName;
    private Address address;
    private String dateDelivery;
    private String timeDelivery;
}
