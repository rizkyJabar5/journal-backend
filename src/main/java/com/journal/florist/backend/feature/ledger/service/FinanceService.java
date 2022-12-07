package com.journal.florist.backend.feature.ledger.service;


import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.ledger.model.Finance;

import java.math.BigDecimal;
import java.util.Date;

public interface FinanceService {

    BaseResponse getFinanceByDate(String date);
    void addFinanceExpense(BigDecimal expenseAmount, BigDecimal totalDebt);
    void addFinancePurchase(BigDecimal purchaseAmount);
    void addAccountReceivableAndRevenue(BigDecimal accountReceivable, BigDecimal revenue);
    String isExistsFinanceToday(Date date);
}
