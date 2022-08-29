package com.journal.florist.backend.feature.order.service;

import com.journal.florist.app.common.utils.HasLogger;
import com.journal.florist.app.common.utils.jasper.JasperReportRequest;

public interface DeliveryNoteService extends HasLogger {

    JasperReportRequest create(String orderId, String gnrId);

}
