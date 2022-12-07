package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.ledger.model.Finance;
import com.journal.florist.backend.feature.ledger.repositories.FinanceRepository;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class FinanceServiceImpl implements FinanceService {

    private final FinanceRepository repository;

    @Override
    public BaseResponse getFinanceByDate(String date) {
        if(date == null || date.isEmpty()) {
            Date financeToday = DateConverter.today();
            Finance financeDate = repository.getFinanceToday(financeToday);

            if(financeDate == null) {
                return new BaseResponse(HttpStatus.NOT_FOUND,
                        "Record not found today",
                        null
                );
            }


            return new BaseResponse(HttpStatus.FOUND,
                    String.format("Record found in date %s", date),
                    financeDate
                    );
        }

        Date dateTime;
        var dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            dateTime = dateFormat.parse(date);
            long time = dateTime.getTime();
            if (time != 0) {
                dateTime = new Date(time);
            }
        } catch (ParseException e) {
            throw new AppBaseException("Format date it's dd-MM-yyyy");
        }

        Finance financeDate = repository.getFinanceToday(dateTime);

        if(financeDate == null) {
            return new BaseResponse(HttpStatus.NOT_FOUND,
                    String.format("Record not found in date %s", date),
                    null
            );
        }

        return new BaseResponse(HttpStatus.FOUND,
                String.format("Record found in date %s", date),
                financeDate
        );
    }

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
    public void addFinancePurchase(BigDecimal purchaseAmount) {
        Date today = DateConverter.today();
        String financeID = isExistsFinanceToday(today);

        if (financeID == null || financeID.isEmpty()) {
            Finance entity = new Finance();
            entity.setTotalDebt(purchaseAmount);
            entity.setFromDaysFinance(new Date(System.currentTimeMillis()));
            repository.saveAndFlush(entity);

            return;
        }

        Finance entity = findFinanceById(financeID);
        entity.setTotalDebt(purchaseAmount);

        repository.save(entity);
    }

    @Override
    public void addAccountReceivableAndRevenue(BigDecimal accountReceivable, BigDecimal revenue) {
        Date today = DateConverter.today();
        String financeID = isExistsFinanceToday(today);

        if (financeID == null || financeID.isEmpty()) {
            Finance entity = new Finance();
            entity.setAccountReceivable(accountReceivable);
            entity.setRevenue(revenue);
            entity.setFromDaysFinance(new Date(System.currentTimeMillis()));
            repository.saveAndFlush(entity);

            return;
        }

        Finance entity = findFinanceById(financeID);
        entity.setAccountReceivable(accountReceivable);
        entity.setRevenue(revenue);

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
