/*
 * Copyright (c) 2022.
 */

package com.journal.florist.backend.feature.order.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentStatus {
    /**
     * Pembayaran yang belum di bayar
     */
    NOT_YET_PAID("Not yet paid"),

    /**
     * Pembayaran lunas
     */
    PAID_OFF("Paid off"),
    /**
     * Pembayaran secara kredit
     */
    CREDIT("Credit"),

    /**
     * Pembayaran di muka
     */
    DOWN_PAYMENT("Down payment");

    private final String name;

    PaymentStatus(String name) {
        this.name = name;
    }

    public static String getAllPaymentStatus() {
        return Arrays.toString(PaymentStatus.class.getEnumConstants());
    }

}
