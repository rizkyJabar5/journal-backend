package com.journal.florist.backend.feature.customer.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.customer.model.CustomerDebt;
import com.journal.florist.backend.feature.customer.model.Customers;

import java.math.BigDecimal;

public interface CustomerDebtService extends HasLogger {
    BigDecimal getDebtByCustomerId(String customerId);
    CustomerDebt addDebtCustomer(Customers customer, BigDecimal totalDebt);
    void subtractDebtCustomer(Customers customer, BigDecimal totalDebt);
    BigDecimal sumAllTotalCustomerDebt();
}
