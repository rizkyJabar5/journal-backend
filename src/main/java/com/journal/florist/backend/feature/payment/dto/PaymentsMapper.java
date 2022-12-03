package com.journal.florist.backend.feature.payment.dto;

import com.journal.florist.app.common.utils.converter.DateConverter;
import com.journal.florist.backend.feature.payment.model.Payments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PaymentsMapper implements Serializable {

    private String paymentID;
    private String orderID;
    private String customerID;
    private String customerName;
    private String paymentStatus;
    private String paymentDate;
    private BigDecimal paymentAmount;
    private BigDecimal underPayment;

    public PaymentsMapper buildPaymentMapper(Payments payment) {
        LocalDateTime dateTime = DateConverter.toLocalDateTime(payment.getPaymentLogs().getPaymentDate());
        String date = DateConverter.formatDateTime().format(dateTime);

        return PaymentsMapper.builder()
                .paymentID(payment.getPaymentID())
                .orderID(payment.getOrder().getPublicKey())
                .customerID(payment.getOrder().getCustomer().getPublicKey())
                .customerName(payment.getOrder().getCustomer().getName())
                .paymentStatus(payment.getOrder().getPaymentStatus().name())
                .paymentDate(date)
                .paymentAmount(payment.getAmount())
                .underPayment(payment.getUnderPayment())
                .build();
    }
}


