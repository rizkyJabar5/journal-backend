package com.journal.florist.backend.feature.ledger.dto.request;

import java.io.Serializable;

/**
 * A DTO for the {@link com.journal.florist.backend.feature.ledger.model.Suppliers} entity
 */
public record AddSupplierRequest(String supplierName) implements Serializable {
}