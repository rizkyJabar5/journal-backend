package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.repositories.SupplierRepository;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.journal.florist.app.constant.JournalConstants.MUST_BE_UNIQUE;

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
    public Page<Suppliers> getAllSuppliers(Pageable pageable) {
        return repository.findAllSuppliers(pageable);
    }

    @Override
    public Page<Suppliers> getAllSuppliersWithNoDebt(Pageable pageable) {
        return repository.findSuppliersWithNoDebt(pageable);
    }

    @Override
    public Page<Suppliers> getAllSuppliersWithDebt(Pageable pageable) {
        return repository.findSuppliersWithDebt(pageable);
    }

    @Override
    public BigDecimal findDebtBySupplier(String supplierId) {
        return repository.findTotalDebtSupplier(supplierId);
    }

    @Override
    public BigDecimal sumTotalDebt() {
        return repository.sumAllTotalDebt();
    }

    @Override
    public BaseResponse addSuppliers(Suppliers supplier) {
        Suppliers entity = new Suppliers();
        String supplierName = supplier.getSupplierName();

        boolean existSupplier = repository.isExistSupplier(supplierName);

        if(existSupplier) {
            throw new IllegalException(
                    String.format(MUST_BE_UNIQUE, supplierName)
            );
        }

        entity.setSupplierName(supplierName);

        repository.save(entity);
        return new BaseResponse(
                HttpStatus.CREATED,
                "Successfully to add new Suppliers",
                entity
        );
    }

    @Override
    public void update(Suppliers suppliers) {
        Suppliers byId = repository.findById(suppliers.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("supplier %s not found", suppliers.getId())));
        repository.save(byId);
    }
}
