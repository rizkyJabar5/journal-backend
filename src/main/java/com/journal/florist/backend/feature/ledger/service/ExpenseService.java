package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.dto.request.ExpenseRequest;
import com.journal.florist.backend.feature.ledger.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ExpenseService extends HasLogger {
    Page<Expense> getAllExpense(Pageable pageable);
    Expense findExpenseById(String expenseId);
    Expense create(ExpenseRequest request);
    BigDecimal sumExpense();
}
