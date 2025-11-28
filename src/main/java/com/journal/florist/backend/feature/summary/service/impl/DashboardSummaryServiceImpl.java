package com.journal.florist.backend.feature.summary.service.impl;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.backend.feature.customer.service.CustomerService;
import com.journal.florist.backend.feature.order.dto.OrdersMapper;
import com.journal.florist.backend.feature.order.service.OrderService;
import com.journal.florist.backend.feature.product.service.ProductService;
import com.journal.florist.backend.feature.summary.dto.SummaryStore;
import com.journal.florist.backend.feature.summary.service.DashboardSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardSummaryServiceImpl implements DashboardSummaryService {

    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @Override
    public BaseResponse summaryStore() {
        long allProduct = productService.countAllProduct();
        long allCustomer = customerService.countAllCustomer();
        List<OrdersMapper> recentOrders = orderService.findRecentOrder();

        SummaryStore summaryStore = SummaryStore.buildSummaryStore(
                allProduct,
                allCustomer,
                recentOrders
        );

        return new BaseResponse(
                HttpStatus.OK,
                "Summary store statistics",
                summaryStore
        );
    }
}
