package com.journal.florist.backend.feature.ledger.service;


import java.math.BigDecimal;
import java.util.Date;

public interface FinanceService {

    void addFinanceExpense(BigDecimal expenseAmount, BigDecimal totalDebt);
    void addFinancePurchase(BigDecimal purchaseAmount);
    void addAccountReceivableAndRevenue(BigDecimal accountReceivable, BigDecimal revenue);
    String isExistsFinanceToday(Date date);
}
