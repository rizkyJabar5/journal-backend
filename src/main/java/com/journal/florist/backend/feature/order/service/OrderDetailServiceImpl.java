package com.journal.florist.backend.feature.order.service;

import com.journal.florist.backend.feature.order.model.OrderDetails;
import com.journal.florist.backend.feature.order.repositories.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailsRepository repository;

    @Override
    public OrderDetails create(OrderDetails request) {
        return repository.save(request);
    }
}
