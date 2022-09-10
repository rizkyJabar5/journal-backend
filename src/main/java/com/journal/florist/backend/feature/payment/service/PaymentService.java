package com.journal.florist.backend.feature.payment.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.payment.dto.PaymentRequest;
import com.journal.florist.backend.feature.payment.dto.PaymentsMapper;
import com.journal.florist.backend.feature.payment.model.Payments;

import java.math.BigDecimal;

public interface PaymentService extends HasLogger {

    Payments findPaymentId(String id);
    Payments addPayment(BigDecimal amount, BigDecimal underPayment, Orders order);
    PaymentsMapper creditPayment(PaymentRequest request);
}
