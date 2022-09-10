package com.journal.florist.backend.feature.payment.repositories;

import com.journal.florist.backend.feature.payment.model.PaymentLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentLogsRepository extends JpaRepository<PaymentLogs, String> {

    @Query("""
            SELECT SUM(pl.paymentAmount) FROM PaymentLogs pl
            """)
    BigDecimal sumAllPaymentAmount();
}