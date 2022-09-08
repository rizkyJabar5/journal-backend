package com.journal.florist.backend.feature.customer.service;

import com.journal.florist.backend.feature.customer.model.CustomerDebt;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.customer.repositories.CustomerDebtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomerDebtServiceImpl implements CustomerDebtService {

    private final CustomerDebtRepository repository;

    @Override
    public void debtCustomer(Customers customer, BigDecimal customerPay) {
        CustomerDebt debt = repository.findCustomerById(customer.getId());
        if(debt == null) {
            CustomerDebt customerDebt = new CustomerDebt();
            customerDebt.setCustomer(customer);
            customerDebt.setTotalDebt(customerPay);
            repository.save(customerDebt);
            return;
        }
        BigDecimal total = debt.getTotalDebt().add(customerPay);
        debt.setTotalDebt(total);
    }

    @Override
    public BigDecimal sumAllTotalCustomerDebt() {
        return repository.sumTotalAccountReceivable();
    }
}
