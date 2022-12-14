package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface SupplierService extends HasLogger {
    Suppliers getSupplierById(String id);
    Page<Suppliers> getAllSuppliers(Pageable pageable);
    Page<Suppliers> getAllSuppliersWithNoDebt(Pageable pageable);
    Page<Suppliers> getAllSuppliersWithDebt(Pageable pageable);
    BigDecimal findDebtBySupplier(String supplierId);
    BigDecimal sumTotalDebt();
    BaseResponse addSuppliers(Suppliers supplier);

    void update(Suppliers suppliers);

}
