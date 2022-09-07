package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.repositories.SupplierRepository;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;

    @Override
    public Suppliers getSupplierById(String id) {
        return repository.findById(id)
                .orElseThrow( () -> new NotFoundException(
                        String.format("supplier %s not found", id)));
    }

    @Override
    public List<Suppliers> getAllSuppliers() {
        return repository.findAllSuppliers();
    }

    @Override
    public BigDecimal findDebtBySupplier(String supplierId) {
        return repository.findTotalDebtSupplier(supplierId);
    }

    @Override
    public void update(Suppliers suppliers) {
        Suppliers byId = repository.findById(suppliers.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("supplier %s not found", suppliers.getId())));
        repository.save(byId);
    }

    @Override
    public Suppliers addSuppliers(Suppliers supplier) {
        return repository.save(supplier);
    }
}
