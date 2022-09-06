package com.journal.florist.backend.feature.ledger.repositories;

import com.journal.florist.backend.feature.ledger.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
