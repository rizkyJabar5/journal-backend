package com.journal.florist.backend.feature.order.repositories;

import com.journal.florist.backend.feature.order.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {

    @Query("SELECT SUM(py.amount) FROM Payments py")
    BigDecimal sumTotalAmountPayment();

}