package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, String> {

    @Query("select s from Suppliers s")
    List<Suppliers> findAllSuppliers();

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
