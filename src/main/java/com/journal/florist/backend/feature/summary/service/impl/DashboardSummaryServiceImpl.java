package com.journal.florist.backend.feature.summary.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.ledger.service.ExpenseService;
import com.journal.florist.backend.feature.ledger.service.FinanceService;
import com.journal.florist.backend.feature.ledger.service.SalesService;
import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import com.journal.florist.backend.feature.order.service.OrderService;
import com.journal.florist.backend.feature.product.service.ProductService;
import com.journal.florist.backend.feature.summary.dto.SummaryLedger;
import com.journal.florist.backend.feature.summary.dto.SummaryStore;
import com.journal.florist.backend.feature.summary.service.DashboardSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardSummaryServiceImpl implements DashboardSummaryService {

    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final SalesService salesService;
    private final FinanceService financeService;
    private final ExpenseService expenseService;

    @Override
    public BaseResponse summaryStore() {
        long allProduct = productService.countAllProduct();
        long allCustomer = customerService.countAllCustomer();
        BigDecimal sumGrossSalesToday = salesService.sumGrossSalesToday();
        BigDecimal sumNetSalesToday = salesService.sumNetSalesToday();
        List<OrdersMapper> recentOrders = orderService.findRecentOrder();

        SummaryStore summaryStore = SummaryStore.buildSummaryStore(
                allProduct,
                allCustomer,
                sumGrossSalesToday,
                sumNetSalesToday,
                recentOrders
        );

        return new BaseResponse(
                HttpStatus.OK,
                "Summary store statistics",
                summaryStore
        );
    }

    @Override
    public BaseResponse summaryLedger() {
        BigDecimal totalDebt = financeService.sumTotalDebt();
        BigDecimal totalRevenue = financeService.sumRevenue();
        BigDecimal accountReceivable = financeService.sumAccountReceivable();
        BigDecimal totalExpense = expenseService.sumExpense();
        BigDecimal grossSales = salesService.sumGrossSales();
        BigDecimal netSales = salesService.sumNetSales();

        SummaryLedger summaryLedger = SummaryLedger.buildSummaryLedger(
                totalDebt,
                totalRevenue,
                accountReceivable,
                totalExpense,
                grossSales,
                netSales
        );

        return new BaseResponse(
                HttpStatus.OK,
                "Summary ledger statistics",
                summaryLedger
        );
    }
}
