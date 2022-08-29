package com.journal.florist.app.common.utils.jasper;

import com.journal.florist.app.common.utils.HasLogger;

public interface ReportService extends HasLogger {

    JasperReportRequest generatePDFReport(JasperReportRequest request);
    void exportToPdf(String outputFilename);
}
