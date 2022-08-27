package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.order.model.OrderShipments;

public interface ShipmentService extends HasLogger {
    OrderShipments create(OrderShipments request);

}
