package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.backend.feature.order.model.OrderDetails;

public interface OrderDetailService extends HasLogger {
    OrderDetails create(OrderDetails request);
}
