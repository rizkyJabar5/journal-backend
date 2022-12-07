package com.journal.florist.backend.feature.summary.service;

import com.journal.florist.app.common.messages.BaseResponse;

public interface DashboardSummaryService {

    BaseResponse summaryStore();
    BaseResponse summaryLedger();
}
