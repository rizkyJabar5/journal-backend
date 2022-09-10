package com.journal.florist.backend.feature.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.feature.order.enums.OrderStatus;
import com.journal.florist.backend.feature.utils.Address;

import javax.validation.Valid;
import java.io.Serializable;

public record UpdateOrderRequest(
        String orderId,
        OrderStatus orderStatus,
        String recipientName,
        @Valid Address address,
        @JsonFormat(pattern = "dd-MM-yyyy") String dateDelivery,
        @JsonFormat(pattern = "HH:mm") String timeDelivery) implements Serializable {
    public void checkOrderId() {
        if (orderId == null) {
            throw new IllegalException("Order ID cannot be null");
        } else if (orderId.isEmpty() || orderId.isBlank()) {
            throw new AppBaseException("Order ID is required or cannot be left blank");
        }
    }
}
