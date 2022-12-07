package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("select e from Expense e " +
            "where e.publicKey = ?1")
    Expense findByExpenseId(String expenseId);
    @Query("""
            select e
            from Expense e
            order by e.createdAt desc
            """)
    Page<Expense> findAllExpensePage(Pageable pageable);
    @Query("""
            select sum(e.amount)
            from Expense e
            """)
    BigDecimal sumTotalExpense();
}
