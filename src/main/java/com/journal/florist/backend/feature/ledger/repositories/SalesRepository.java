package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    @Query("select s from Sales s " +
            "where cast(s.saleDate as date) = cast(?1 as date)")
    List<Sales> findAllBySaleDate(Date date);

    @Query("select s from Sales s")
    Page<Sales> findAllSales(Pageable pageable);
    @Query("""
            select sum(s.netProfit)
            from Sales s
            where cast(s.saleDate as date) = cast(?1 as date)
            """)
    BigDecimal sumNetProfitToday(Date date);
    @Query("""
            select sum(s.salesAmount)
            from Sales s
            where cast(s.saleDate as date) = cast(?1 as date)
            """)
    BigDecimal sumGrossSalesToday(Date date);
    @Query("""
            select sum(s.salesAmount)
            from Sales s
            """)
    BigDecimal sumGrossSales();
    @Query("""
            select sum(s.netProfit)
            from Sales s
            """)
    BigDecimal sumNetSales();
}
