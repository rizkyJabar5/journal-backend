package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.dto.request.PurchaseRequest;
import com.journal.florist.backend.feature.ledger.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseService extends HasLogger {
    Page<Purchase> getAllSuppliers(Pageable pageable);
    BaseResponse create(PurchaseRequest request);
}
