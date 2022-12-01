package com.journal.florist.backend.feature.payment.service;

import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.customer.service.CustomerDebtService;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import com.journal.florist.backend.feature.order.enums.PaymentStatus;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.payment.dto.PaymentRequest;
import com.journal.florist.backend.feature.payment.dto.PaymentsMapper;
import com.journal.florist.backend.feature.payment.model.PaymentLogs;
import com.journal.florist.backend.feature.payment.model.Payments;
import com.journal.florist.backend.feature.payment.repositories.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static com.journal.florist.app.constant.JournalConstants.NOT_FOUND_MSG;
import static com.journal.florist.backend.feature.order.enums.PaymentStatus.CREDIT;
import static com.journal.florist.backend.feature.order.enums.PaymentStatus.PAID_OFF;

@RequiredArgsConstructor
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentsRepository repository;
    private final PaymentLogService paymentLogService;
    private final PaymentsMapper paymentMapper;
    private final CustomerDebtService customerDebtService;
    private final FinanceService financeService;

    @Override
    public Payments findPaymentId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, Payments.class.getName(), id)));
    }

    @Override
    public Payments addPayment(BigDecimal amount, BigDecimal underPayment, Orders order) {

        Payments entity = new Payments();
        PaymentLogs paymentLogs = new PaymentLogs();

        entity.setOrder(order);
        entity.setPaymentDate(new Date(System.currentTimeMillis()));
        entity.setAmount(amount);
        entity.setUnderPayment(underPayment);

        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            paymentLogs.setPaymentAmount(amount);
            entity.setPaymentLogs(paymentLogs);
            paymentLogs.setPaymentDate(new Date(System.currentTimeMillis()));
        }

        return repository.saveAndFlush(entity);
    }

    @Override
    public PaymentsMapper creditPayment(PaymentRequest request) {
        request.checkOrderIdNullOrNot();
        request.checkPaymentAmount();

        PaymentLogs paymentLogs = new PaymentLogs();
        Payments entity = Optional.ofNullable(repository.findPaymentByOrderId(request.orderId()))
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, "Order with id", request.orderId()))
                );

        PaymentStatus paymentStatus = entity.getOrder().getPaymentStatus();

        if (paymentStatus.equals(PaymentStatus.PAID_OFF)) {
            throw new AppBaseException(String.format("Order with id %s there is no bill", request.orderId()));
        }

        BigDecimal result = entity.getUnderPayment().subtract(request.paymentAmount());

        if (result.compareTo(BigDecimal.ZERO) == 0) {
            entity.getOrder().setPaymentStatus(PAID_OFF);
            entity.setAmount(request.paymentAmount());
            entity.setUnderPayment(result);

            paymentLogs.setPaymentAmount(entity.getAmount());
            paymentLogs.setPaymentDate(new Date(System.currentTimeMillis()));

            entity.setPaymentLogs(paymentLogs);

            customerDebtService.subtractDebtCustomer(entity.getOrder().getCustomer(), result);
            financeService.addAccountReceivableAndRevenue(
                    customerDebtService.sumAllTotalCustomerDebt(),
                    paymentLogService.sumTotalAmountPayment());
        } else if (result.compareTo(BigDecimal.ZERO) > 0) {
            entity.getOrder().setPaymentStatus(CREDIT);
            entity.setAmount(request.paymentAmount());
            entity.setUnderPayment(result);

            paymentLogs.setPaymentAmount(entity.getAmount());
            paymentLogs.setPaymentDate(new Date(System.currentTimeMillis()));

            entity.setPaymentLogs(paymentLogs);

            customerDebtService.subtractDebtCustomer(entity.getOrder().getCustomer(), result);
            financeService.addAccountReceivableAndRevenue(
                    customerDebtService.sumAllTotalCustomerDebt(),
                    paymentLogService.sumTotalAmountPayment());
        } else {
            BigDecimal paymentOver = request.paymentAmount().subtract(entity.getUnderPayment());
            throw new AppBaseException(String.format("Customer payment is over +%s", paymentOver));
        }

//        entity.setPaymentLogs(paymentLogs);

        Payments save = repository.save(entity);
        return paymentMapper.buildPaymentMapper(save);
    }
}
