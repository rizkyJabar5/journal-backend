package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Suppliers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, String> {

    @Query("select s from Suppliers s")
    Page<Suppliers> findAllSuppliers(Pageable pageable);

    @Query("""
            select s.totalDebt from Suppliers s
            where s.id = ?1
            """)
    BigDecimal findTotalDebtSupplier(String supplierId);

    @Query("""
            select sum(s.totalDebt) 
            from Suppliers s
            """)
    BigDecimal sumAllTotalDebt();
}
