package com.journal.florist.backend.feature.order.service;

import com.journal.florist.backend.feature.order.model.OrderShipments;
import com.journal.florist.backend.feature.order.repositories.OrderShipmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final OrderShipmentsRepository repository;

    @Override
    public OrderShipments create(OrderShipments request) {
        return repository.save(request);
    }
}
