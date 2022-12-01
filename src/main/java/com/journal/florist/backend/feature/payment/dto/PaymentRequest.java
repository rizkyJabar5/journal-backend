package com.journal.florist.backend.feature.payment.dto;

import com.journal.florist.backend.exceptions.IllegalException;

import java.math.BigDecimal;

public record PaymentRequest(
        String orderId,
        BigDecimal paymentAmount) {

    public void checkOrderIdNullOrNot() {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalException("Order id can't be null or empty");
        }
    }

    public void checkPaymentAmount() {
        if(paymentAmount.compareTo(BigDecimal.ZERO) < 0
                || paymentAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalException("Payment amount can't be negative");
        }
    }
}
