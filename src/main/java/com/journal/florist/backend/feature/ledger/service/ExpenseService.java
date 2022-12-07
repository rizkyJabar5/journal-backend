package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.dto.request.ExpenseRequest;
import com.journal.florist.backend.feature.ledger.model.Expense;

import java.math.BigDecimal;

public interface ExpenseService extends HasLogger {

    Expense create(ExpenseRequest request);
    BigDecimal sumExpense();
}
