package com.journal.florist.backend.feature.summary.dto;

import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public final class SummaryStore implements Serializable {
    private Long totalProducts;
    private Long totalCustomers;
    private BigDecimal grossSalesToday;
    private BigDecimal netProfitSalesToday;
    private Object recentOrders;

    public static SummaryStore buildSummaryStore(
            Long totalProducts,
            Long totalCustomers,
            BigDecimal grossSalesToday,
            BigDecimal netProfitSalesToday,
            List<OrdersMapper> recentOrders) {

        if (totalProducts == null) {
            totalProducts = 0L;
        }
        if (totalCustomers == null) {
            totalCustomers = 0L;
        }
        if (grossSalesToday == null) {
            grossSalesToday = BigDecimal.ZERO;
        }
        if (netProfitSalesToday == null) {
            netProfitSalesToday = BigDecimal.ZERO;
        }
        if (recentOrders == null) {
            recentOrders = Collections.emptyList();
        }

        return SummaryStore.builder()
                .totalProducts(totalProducts)
                .totalCustomers(totalCustomers)
                .grossSalesToday(grossSalesToday)
                .netProfitSalesToday(netProfitSalesToday)
                .recentOrders(recentOrders)
                .build();
    }
}
