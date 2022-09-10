package com.journal.florist.backend.feature.payment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payment_log")
public class PaymentLogs {

    @Id
    @Column(name = "payment_log_id")
    private String logId = UUID.randomUUID().toString().replace("-", "");

    private BigDecimal paymentAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

}
