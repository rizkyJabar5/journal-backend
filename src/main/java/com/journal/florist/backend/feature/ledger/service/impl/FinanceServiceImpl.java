package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.app.common.utils.DateConverter;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.ledger.model.Finance;
import com.journal.florist.backend.feature.ledger.repositories.FinanceRepository;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class FinanceServiceImpl implements FinanceService {

    private final FinanceRepository repository;

    @Override
    public void addFinanceExpense(BigDecimal expense, BigDecimal totalDebt) {
        Date today = DateConverter.today();
        String financeID = isExistsFinanceToday(today);

        if (financeID == null || financeID.isEmpty()) {
            Finance entity = new Finance();
            entity.setExpense(expense);
            entity.setFromDaysFinance(new Date(System.currentTimeMillis()));
            entity.setTotalDebt(totalDebt);
            repository.saveAndFlush(entity);

            return;
        }

        Finance entity = findFinanceById(financeID);
        BigDecimal add = entity.getExpense().add(expense);
        entity.setExpense(add);
        entity.setTotalDebt(totalDebt);

        repository.save(entity);
    }

    @Override
    public void update(Finance finance) {
        Finance entity = repository.findById(finance.getFinanceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Finance %s not found", finance.getFinanceId())
                ));
        repository.save(entity);
    }

    @Override
    public String isExistsFinanceToday(Date date) {
        return repository.findFinanceIdByToday(date);
    }

    private Finance findFinanceById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Finance %s not found", id)
                ));
    }
}
