package com.journal.florist.backend.feature.ledger.dto.request;

import com.journal.florist.backend.feature.ledger.enums.Pay;

import java.math.BigDecimal;

public record ExpenseRequest(
        String supplierId,
        String additionalInformation,
        BigDecimal amount,
        Pay pay) {}
