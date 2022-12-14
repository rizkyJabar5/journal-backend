package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, String> {

    @Query("""
            select f.financeId
            from Finance f
            where cast(f.fromDaysFinance as date) = cast(?1 as date)
            """)
    String findFinanceIdByToday(Date today);

    @Query("""
            select f
            from Finance f
            where cast(f.fromDaysFinance as date) = cast(?1 as date)
            """)
    Finance getFinanceToday(Date today);

    @Query("""
            select sum(f.totalDebt)
            from Finance f
            """)
    BigDecimal sumTotalDebt();

    @Query("""
            select sum(f.revenue)
            from Finance f
            """)
    BigDecimal sumTotalRevenue();

    @Query("""
            select sum(f.accountReceivable)
            from Finance f
            """)
    BigDecimal sumTotalAccountReceivable();
}
