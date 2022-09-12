package com.journal.florist.backend.feature.payment.repositories;

import com.journal.florist.backend.feature.payment.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {


    @Query("select (count(p) > 0) from Payments p where p.order.id = ?1")
    boolean existsPaymentByOrderId(String orderId);

    @Query("""
            SELECT py
            FROM Payments py
            WHERE py.order.publicKey = ?1
            """)
    Payments findPaymentByOrderId(String orderId);
}