package com.journal.florist.backend.feature.order.enums;

public enum OrderStatus {
    /**
     * The order will be taken by customer on the store
     */
    TAKEN("Taken"),
    /**
     * The order pending for some reason
     */
    PENDING("Pending"),
    /**
     * The order is cancelled
     */
    CANCELED("Canceled"),
    /**
     * The order is sent to location delivery address
     */
    SENT("Sent");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
