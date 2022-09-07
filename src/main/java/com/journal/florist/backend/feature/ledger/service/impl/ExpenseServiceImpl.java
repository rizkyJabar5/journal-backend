package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.feature.ledger.dto.request.ExpenseRequest;
import com.journal.florist.backend.feature.ledger.enums.Pay;
import com.journal.florist.backend.feature.ledger.model.Expense;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.repositories.ExpenseRepository;
import com.journal.florist.backend.feature.ledger.service.ExpenseService;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final SupplierService supplierService;
    private final FinanceService financeService;

    @Override
    @Transactional
    public Expense create(ExpenseRequest request) {
        String createdBy = SecurityUtils.getAuthentication().getName();
        Expense expense = new Expense();

        expense.setAdditionalInformation(request.additionalInformation());
        expense.setAmount(request.amount());
        expense.setPayFor(request.pay());
        expense.setCreatedBy(createdBy);
        expense.setCreatedAt(new Date(System.currentTimeMillis()));

        Suppliers supplier = supplierService.getSupplierById(request.supplierId());
        if (request.pay() == Pay.SUPPLIERS) {
            BigDecimal debtBySupplier = supplierService.findDebtBySupplier(supplier.getId());
            BigDecimal result = debtBySupplier.subtract(expense.getAmount());
            if (debtBySupplier.compareTo(BigDecimal.ZERO) == 0) {
                throw new AppBaseException(
                        String.format(
                                "Do not have debt to suppliers %s",
                                supplier.getId())
                );
            } else if (result.compareTo(BigDecimal.ZERO) < 0) {
                BigDecimal overPayment = expense.getAmount().subtract(debtBySupplier);
                throw new AppBaseException(
                        String.format("Debt is only %s your payment is over +%s", debtBySupplier, overPayment));
            }
            supplier.setTotalDebt(result);
        }
        expenseRepository.save(expense);
        BigDecimal totalDebt = supplierService.sumTotalDebt();
        financeService.addFinanceExpense(expense.getAmount(), totalDebt);
        supplierService.update(supplier);

        return expense;
    }
}
