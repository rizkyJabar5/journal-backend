package com.journal.florist.backend.feature.customer.repositories;

import com.journal.florist.backend.feature.customer.model.CustomerDebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CustomerDebtRepository extends JpaRepository<CustomerDebt, String> {

    @Query("SELECT SUM(cd.totalDebt) FROM CustomerDebt cd ")
    BigDecimal sumTotalAccountReceivable();

    @Query("""
            SELECT cd
            FROM CustomerDebt cd
            where cd.customer.publicKey = ?1
            """)
    BigDecimal getDebtByCustomerId(String customerId);

    @Query("""
            select cd
            from CustomerDebt cd
            where cd.customer.publicKey = :customerId
            """)
    CustomerDebt findCustomerById(String customerId);
}