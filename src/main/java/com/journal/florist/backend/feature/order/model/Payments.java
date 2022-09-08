package com.journal.florist.backend.feature.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

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

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id",
            foreignKey = @ForeignKey(name = "order_fk_id"))
    private Orders order;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(name = "amount")
    private BigDecimal amount;
    private BigDecimal underPayment;

    @PrePersist
    private void generateRandomId() {
        if (paymentID != null) {
            return;
        }
        setPaymentID(RandomStringUtils.randomAlphanumeric(16));
    }
}
