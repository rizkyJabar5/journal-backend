package com.journal.florist.backend.feature.ledger.service;


import com.journal.florist.backend.feature.ledger.model.Finance;

import java.math.BigDecimal;
import java.util.Date;

public interface FinanceService {

    void addFinanceExpense(BigDecimal expenseAmount, BigDecimal totalDebt);
    void update(Finance finance);
    String isExistsFinanceToday(Date date);
}
