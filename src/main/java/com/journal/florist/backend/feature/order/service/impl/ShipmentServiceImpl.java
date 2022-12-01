package com.journal.florist.backend.feature.order.service.impl;

import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.order.model.OrderShipments;
import com.journal.florist.backend.feature.order.model.Orders;
import com.journal.florist.backend.feature.order.repositories.OrderShipmentsRepository;
import com.journal.florist.backend.feature.order.service.ShipmentService;
import com.journal.florist.backend.feature.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.journal.florist.app.constant.JournalConstants.NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final OrderShipmentsRepository repository;

    @Override
    public OrderShipments create(OrderShipments request) {
        return repository.saveAndFlush(request);
    }

    @Override
    public OrderShipments findShipmentByOrderId(String orderId) {
        return Optional.ofNullable(repository.findByOrderPublicKey(orderId))
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Orders.class), orderId)));
    }
}
