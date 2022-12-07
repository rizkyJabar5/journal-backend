package com.journal.florist.backend.feature.ledger.enums;

import lombok.Getter;

@Getter
public enum Pay {
    SUPPLIERS("Supplier"),
    OPERATIONAL("Operational");

    private final String name;

    Pay(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
