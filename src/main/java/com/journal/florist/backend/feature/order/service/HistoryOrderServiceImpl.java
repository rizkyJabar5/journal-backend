package com.journal.florist.backend.feature.order.service;

import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.order.model.HistoryOrders;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.HistoryOrderRepositories;
import com.journal.florist.backend.feature.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HistoryOrderServiceImpl implements HistoryOrderService {

    private final HistoryOrderRepositories historyRepository;

    @Override
    @Transactional
    public void saveHistoryOrder(Orders orders, Customers customer, Product product) {

        HistoryOrders entity = new HistoryOrders();
        entity.setOrders(orders);
        entity.setCustomers(customer);
        entity.setProduct(product);
        getLogger().info("Saving history order for customer " + customer.getName());
        historyRepository.save(entity);
    }

    @Override
    public Page<HistoryOrders> getAllHistoryOrder(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }
}
