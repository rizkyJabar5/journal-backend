package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.ledger.model.Suppliers;

import java.util.List;

public interface SupplierService extends HasLogger {
    Suppliers getSupplierById(String id);
    List<Suppliers> getAllSuppliers();

    void update(Suppliers suppliers);

    Suppliers addSuppliers(Suppliers supplier);


}
