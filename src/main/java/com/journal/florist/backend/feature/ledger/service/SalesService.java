package com.journal.florist.backend.feature.ledger.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.ledger.dto.SalesMapper;
import com.journal.florist.backend.feature.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface SalesService extends HasLogger {

    void saveSales(Orders orders, Customers customer);
    Page<SalesMapper> getAllSalesReport(Pageable pageable);
    BigDecimal sumNetSalesToday();
    BigDecimal sumGrossSalesToday();
    default BigDecimal getSalesMonthly() {
        return null;
    }
    BigDecimal sumGrossSales();
    BigDecimal sumNetSales();

}
