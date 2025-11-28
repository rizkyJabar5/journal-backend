package com.journal.florist.backend.feature.summary.dto;

import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public final class SummaryStore implements Serializable {
    private Long totalProducts;
    private Long totalCustomers;
    private Object recentOrders;

    public static SummaryStore buildSummaryStore(
            Long totalProducts,
            Long totalCustomers,
            List<OrdersMapper> recentOrders) {

        if (totalProducts == null) {
            totalProducts = 0L;
        }
        if (totalCustomers == null) {
            totalCustomers = 0L;
        }
        if (recentOrders == null) {
            recentOrders = Collections.emptyList();
        }

        return SummaryStore.builder()
                .totalProducts(totalProducts)
                .totalCustomers(totalCustomers)
                .recentOrders(recentOrders)
                .build();
    }
}
