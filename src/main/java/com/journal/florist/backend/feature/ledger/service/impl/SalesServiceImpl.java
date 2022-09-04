package com.journal.florist.backend.feature.ledger.service.impl;

import com.journal.florist.app.common.utils.DateConverter;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.ledger.dto.SalesMapper;
import com.journal.florist.backend.feature.ledger.model.Sales;
import com.journal.florist.backend.feature.ledger.repositories.SalesRepositories;
import com.journal.florist.backend.feature.ledger.service.SalesService;
import com.journal.florist.backend.feature.order.model.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {

    private final SalesRepositories salesRepository;
    private final SalesMapper salesMapper;

    @Override
    @Transactional
    public void saveSales(Orders orders, Customers customer) {
        Sales entity = new Sales();
        List<Sales> saleToday = salesRepository.findAllBySaleDate(DateConverter.today());
        if (saleToday.isEmpty()) {
            entity.setSalesAmount(orders.getTotalOrderAmount());
            entity.setNetProfit(orders.getTotalOrderCostPrice());
        }

        entity.setOrders(orders);
        entity.setCustomers(customer);
        entity.setSaleDate(orders.getCreatedAt());
        entity.setTotalOrderAmount(orders.getTotalOrderAmount());
        entity.setTotalOrderCostPrice(orders.getTotalOrderCostPrice());

        saleToday.stream()
                .map(Sales::getNetProfit)
                .forEach(netProfit -> {
                    BigDecimal add = netProfit.add(orders.getTotalOrderCostPrice());
                    entity.setNetProfit(add);
                });
        saleToday.stream()
                .map(Sales::getSalesAmount)
                .forEach(saleAmount -> {
                    BigDecimal add = saleAmount.add(entity.getTotalOrderAmount());
                    entity.setSalesAmount(add);
                });
        getLogger().info("Saving history order for customer " + customer.getName());
        salesRepository.save(entity);
    }


    @Override
    public Page<SalesMapper> getAllSalesReport(Pageable pageable) {
        return salesRepository.findAllSales(pageable)
                .map(salesMapper::buildSalesResponse);
    }
}
