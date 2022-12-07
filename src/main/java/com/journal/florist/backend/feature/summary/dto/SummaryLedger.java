package com.journal.florist.backend.feature.summary.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public final class SummaryLedger implements Serializable {
    private BigDecimal debtStore;
    private BigDecimal totalRevenue;
    private BigDecimal accountReceivable;
    private BigDecimal totalExpense;
    private BigDecimal totalGrossSales;
    private BigDecimal totalNetSales;

    public static SummaryLedger buildSummaryLedger(
            BigDecimal debtStore,
            BigDecimal totalRevenue,
            BigDecimal accountReceivable,
            BigDecimal totalExpense,
            BigDecimal totalGrossSales,
            BigDecimal totalNetSales) {

        if(debtStore == null) {
            debtStore = BigDecimal.ZERO;
        }
        if(totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }
        if(accountReceivable == null) {
            accountReceivable = BigDecimal.ZERO;
        }
        if(totalExpense == null) {
            totalExpense = BigDecimal.ZERO;
        }
        if(totalGrossSales == null) {
            totalGrossSales = BigDecimal.ZERO;
        }
        if(totalNetSales == null) {
            totalNetSales = BigDecimal.ZERO;
        }

        return SummaryLedger.builder()
                .debtStore(debtStore)
                .totalRevenue(totalRevenue)
                .accountReceivable(accountReceivable)
                .totalExpense(totalExpense)
                .totalGrossSales(totalGrossSales)
                .totalNetSales(totalNetSales)
                .build();
    }
}
