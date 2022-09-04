package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalesRepositories extends JpaRepository<Sales, Long> {

    @Query("select s from Sales s " +
            "where cast(s.saleDate as date) = cast(?1 as date)")
    List<Sales> findAllBySaleDate(Date date);

    @Query("select s from Sales s")
    Page<Sales> findAllSales(Pageable pageable);

}
