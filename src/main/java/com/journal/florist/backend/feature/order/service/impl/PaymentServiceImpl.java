package com.journal.florist.backend.feature.order.service.impl;

import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.model.Payments;
import com.journal.florist.backend.feature.order.repositories.PaymentsRepository;
import com.journal.florist.backend.feature.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static com.journal.florist.app.constant.JournalConstants.NOT_FOUND_MSG;

@RequiredArgsConstructor
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentsRepository repository;

    @Override
    public Payments findPaymentId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, Payments.class.getName(), id)));
    }

    @Override
    public BigDecimal sumRevenueToday() {
        return repository.sumTotalAmountPayment();
    }

    @Override
    public Payments addPayment(BigDecimal amount, BigDecimal underPayment, Orders order) {
        Payments entity = new Payments();
        entity.setOrder(order);
        entity.setPaymentDate(new Date(System.currentTimeMillis()));
        entity.setAmount(amount);
        entity.setUnderPayment(underPayment);

        return repository.save(entity);
    }
}
