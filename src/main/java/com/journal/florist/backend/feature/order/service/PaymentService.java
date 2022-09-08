package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.model.Payments;

import java.math.BigDecimal;

public interface PaymentService extends HasLogger {

    Payments findPaymentId(String id);

    BigDecimal sumRevenueToday();

    Payments addPayment(BigDecimal amount, BigDecimal underPayment, Orders order);
}
