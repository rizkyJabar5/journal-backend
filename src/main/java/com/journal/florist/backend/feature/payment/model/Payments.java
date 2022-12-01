package com.journal.florist.backend.feature.payment.model;

import com.journal.florist.backend.feature.order.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @Column(name = "payment_id")
    private String paymentID;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(
            name = "order_id",
            foreignKey = @ForeignKey(name = "order_fk_id"))
    private Orders order;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal underPayment = BigDecimal.ZERO;

    @ManyToOne(targetEntity = PaymentLogs.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_logs_id",
            foreignKey = @ForeignKey(name = "payment_logs_fk_id"))
    private PaymentLogs paymentLogs;

    @PrePersist
    private void generateRandomId() {
        if (paymentID != null) {
            return;
        }
        setPaymentID(RandomStringUtils.randomAlphanumeric(16));
    }
}
