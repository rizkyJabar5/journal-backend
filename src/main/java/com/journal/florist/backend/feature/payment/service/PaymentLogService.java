package com.journal.florist.backend.feature.payment.service;

import com.journal.florist.app.common.utils.HasLogger;

import java.math.BigDecimal;

public interface PaymentLogService extends HasLogger {

    BigDecimal sumTotalAmountPayment();
}
