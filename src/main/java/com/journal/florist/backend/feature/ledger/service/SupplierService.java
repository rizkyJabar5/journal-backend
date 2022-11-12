package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.model.Suppliers;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierService extends HasLogger {
    Suppliers getSupplierById(String id);
    List<Suppliers> getAllSuppliers();
    BigDecimal findDebtBySupplier(String supplierId);
    BigDecimal sumTotalDebt();
    void update(Suppliers suppliers);

    BaseResponse addSuppliers(Suppliers suppliers);

}
