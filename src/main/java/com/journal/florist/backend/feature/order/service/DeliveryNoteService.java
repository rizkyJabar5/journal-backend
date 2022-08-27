package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.utils.HasLogger;

public interface DeliveryNoteService extends HasLogger {

    BaseResponse create(String orderId, String gnrId);

}
