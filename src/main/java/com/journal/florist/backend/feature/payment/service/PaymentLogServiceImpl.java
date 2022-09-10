package com.journal.florist.backend.feature.payment.service;

import com.journal.florist.backend.feature.payment.repositories.PaymentLogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    private final PaymentLogsRepository repository;
    @Override
    public BigDecimal sumTotalAmountPayment() {

        return repository.sumAllPaymentAmount();
    }
}
