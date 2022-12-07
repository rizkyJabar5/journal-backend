package com.journal.florist.backend.feature.ledger.dto.request;

import com.journal.florist.backend.feature.ledger.enums.Pay;
import com.journal.florist.backend.feature.ledger.model.Expense;

import java.math.BigDecimal;

public record ExpenseRequest(
        String supplierId,
        String additionalInformation,
        BigDecimal amount,
        Pay pay) {

    public void defaultPay(Expense expense) {
        if (pay == null) {
             expense.setPayFor(Pay.OPERATIONAL);
        }
    }

    public void payDebtForSupplier(Expense expense) {
        expense.setPayFor(Pay.SUPPLIERS);
    }
}
