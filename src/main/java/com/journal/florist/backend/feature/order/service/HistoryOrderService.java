package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.model.HistoryOrders;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryOrderService extends HasLogger {

    void saveHistoryOrder(Orders orders, Customers customer, Product product);
    Page<HistoryOrders> getAllHistoryOrder(Pageable pageable);
}
