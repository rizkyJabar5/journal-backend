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
    public BigDecimal getDebtByCustomerId(String customerId) {
        BigDecimal debt = repository.getDebtByCustomerId(customerId);
        if(debt == null ) {
            debt = BigDecimal.ZERO;
        }

        return debt;
    }

    @Override
    public CustomerDebt addDebtCustomer(Customers customer, BigDecimal totalDebt) {
        CustomerDebt debt = repository.findCustomerById(customer.getPublicKey());
        if(debt == null) {
            CustomerDebt customerDebt = new CustomerDebt();
            customerDebt.setCustomer(customer);
            customerDebt.setTotalDebt(totalDebt);

            return repository.save(customerDebt);
        }
        BigDecimal total = debt.getTotalDebt().add(totalDebt);
        debt.setTotalDebt(total);

        return repository.saveAndFlush(debt);
    }

    @Override
    public void subtractDebtCustomer(Customers customer, BigDecimal totalDebt) {
        CustomerDebt debt = repository.findCustomerById(customer.getPublicKey());

        BigDecimal total = debt.getTotalDebt().subtract(totalDebt);
        debt.setTotalDebt(total);
        repository.save(debt);
    }


    @Override
    public BigDecimal sumAllTotalCustomerDebt() {
        return repository.sumTotalAccountReceivable();
    }
}
