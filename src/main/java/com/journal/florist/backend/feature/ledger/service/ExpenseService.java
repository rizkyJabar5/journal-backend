package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.dto.request.ExpenseRequest;
import com.journal.florist.backend.feature.ledger.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService extends HasLogger {
    Page<Expense> getAllExpense(Pageable pageable);
    List<Expense> getAllExpenseToSuppliers();
    Expense findExpenseById(String expenseId);
    Expense create(ExpenseRequest request);
    BigDecimal sumExpense();
}
