package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.ledger.dto.request.PurchaseRequest;
import com.journal.florist.backend.feature.ledger.model.Purchase;
import com.journal.florist.backend.feature.ledger.model.Suppliers;
import com.journal.florist.backend.feature.ledger.repositories.PurchaseRepository;
import com.journal.florist.backend.feature.ledger.service.PurchaseService;
import com.journal.florist.backend.feature.ledger.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static com.journal.florist.app.constant.JournalConstants.NOT_FOUND_MSG;

@RequiredArgsConstructor
@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierService supplierService;

    @Override
    public Page<Purchase> getAllSuppliers(Pageable pageable) {
        return purchaseRepository.findAllPurchase(pageable);
    }

    @Override
    public BaseResponse create(PurchaseRequest request) {
        Purchase purchase = new Purchase();
        String createdBy = SecurityUtils.getAuthentication().getName();

        Suppliers suppliers = Optional.ofNullable(supplierService.getSupplierById(request.getSupplierId()))
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, Suppliers.class.getName(), request.getSupplierId())));

        purchase.setSuppliers(suppliers);
        purchase.setProductName(request.getProductName());
        purchase.setQuantity(request.getQuantity());
        purchase.setPrice(request.getPrice());
        purchase.setTotal(purchase.setTotalPurchase());
        purchase.setCreatedBy(createdBy);
        purchase.setCreatedAt(new Date(System.currentTimeMillis()));

        getLogger().info("Successfully to persist new purchase");
        purchaseRepository.save(purchase);

        suppliers.setTotalDebt(purchase.addDebt());
        supplierService.update(suppliers);

        return new BaseResponse(HttpStatus.OK,
                "Successfully add new purchase",
                purchase);
    }
}
